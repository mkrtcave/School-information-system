package cz.cvut.fel.ear.model;

public enum Role {
    ADMIN("ROLE_ADMIN"), STUDENT("ROLE_STUDENT"), TEACHER("ROLE_TEACHER");
    
    private final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
