package azaryan;

import azaryan.annotation.*;
import azaryan.domain.CustomerDto;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.regex.Pattern;

class Main {

    private static final String REGEX =
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]" +
                    "+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9" +
                    "9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$ ";

    public static void main(String[] args) throws IllegalAccessException {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("Habet");
        customerDto.setEmail("azaryann.habet@gmail.com");
        customerDto.setBirthDay(LocalDate.of(1996, 3, 20));
        customerDto.setDiscountRate(50);

        print(customerDto);
    }

    static void print(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String value = field.get(obj).toString();
            String name = field.getName();

            if (field.isAnnotationPresent(Email.class)) {
                String email = field.get(obj).toString();
                Pattern compile = Pattern.compile(REGEX);
                if (!compile.matcher(email).matches()) {
                    System.out.println("Invalid Email ");
                }
            }
            else if (field.isAnnotationPresent(Length.class)) {
                Length annotation = field.getAnnotation(Length.class);
                if (name.length() < annotation.min() || name.length() > annotation.max()) {
                    String msg = " Write name for this range" +
                            "(" + annotation.min() + "," + annotation.max() + ")";
                    System.out.println(msg);
                }
            }
            else if (field.isAnnotationPresent(Adulthood.class)) {
                Adulthood nameAnnot = field.getAnnotation(Adulthood.class);
                name = nameAnnot.toString();
            }
            else if (field.isAnnotationPresent(Min.class) && field.isAnnotationPresent(Max.class)) {
                Min annMin = field.getAnnotation(Min.class);
                Max annMax = field.getAnnotation(Max.class);
                int discountRate = field.get(obj).hashCode();

                if (discountRate < annMin.value()) {
                    System.out.println("Your number is less than the discount rate");
                } else if (discountRate > annMax.value()) {
                    System.out.println("Your number is greater than the discount rate");
                }
            }
            System.out.println(field.getName() + " = " + value);
        }
    }
}
