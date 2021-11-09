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
                Pattern pattern = Pattern.compile(REGEX);
                Email nameAnnot = field.getAnnotation(Email.class);
                name = nameAnnot.toString();
            } else if (field.isAnnotationPresent(Length.class)) {
                Length nameAnnot = field.getAnnotation(Length.class);
                name = nameAnnot.toString();
            } else if (field.isAnnotationPresent(Adulthood.class)) {
                Adulthood nameAnnot = field.getAnnotation(Adulthood.class);
                name = nameAnnot.toString();
            } else if (field.isAnnotationPresent(Max.class)) {
                Max nameAnnot = field.getAnnotation(Max.class);
                name = nameAnnot.toString();
            } else if (field.isAnnotationPresent(Min.class)) {
                Min nameAnnot = field.getAnnotation(Min.class);
                name = nameAnnot.toString();
            }
            System.out.println(field.getName() + " = " + value);
        }
    }
}
