package es.leanmind.barbershop.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Establishment {

    private String name;
    private Set<Owners> owners;

    public Establishment(String name, Owners... owners) {
        this.name = name;
        this.owners = new HashSet<>(Arrays.asList(owners));
    }

    public String name() {
        return name;
    }

    public Set<Owners> owners() {
        return new HashSet<>(owners);
    }
}
