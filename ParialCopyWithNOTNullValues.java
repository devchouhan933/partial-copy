package com.partial.copy;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

public class ParialCopyWithNOTNullValues {


    public static void main(String[] args) {
        UserDTO source = UserDTO.builder().name("Name").build();
        User user = User.builder().name("-").address("address").build();
        ParialCopyWithNOTNullValues test = new ParialCopyWithNOTNullValues();
        test.copyNonNullProperties(source, user);
        System.out.println(user);

    }

    public void copyNonNullProperties(UserDTO source, User destination) {
        BeanUtils.copyProperties(source, destination, getNullPropertyNames(source));
    }

    /**
     * Returns an array of null properties of an object
     *
     * @param source
     * @return
     */
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set emptyNames = new HashSet();
        for (java.beans.PropertyDescriptor pd : pds) {
            //check if value of this property is null then add it to the collection
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return (String[]) emptyNames.toArray(result);
    }
}

@Data
@Builder
class UserDTO {
    private String name;
    private String address;
    //getters and setters
}

@Data
@Builder
class User {
    private String name;
    private String address;
    //getters and setters
}

