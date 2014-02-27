package com.jnape.devnexus.demo.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefensiveCopy {

    public static void main(String[] args) {
        Person person = new Person("Bob", "Smith", Arrays.<Object>asList(1, "2", '3'));
        person.getObjects().add(4);
        System.out.println(person.getObjects());
    }

    public static <Element> List<Element> ofList(List<Element> elements) {
        ArrayList<Element> defensiveCopy = new ArrayList<Element>();
        defensiveCopy.addAll(elements);
        return defensiveCopy;
    }

    public static class Person {

        private final String       firstName;
        private final String       lastName;
        private final List<Object> objects;

        public Person(String firstName, String lastName, List<Object> objects) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.objects = objects;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public List<Object> getObjects() {
            return DefensiveCopy.ofList(objects);
        }
    }
}
