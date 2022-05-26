package com.fadedbytes.colegio.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Person {

    private String name;
    private String email;

    public Person(@NotNull Person preset) {
        this(preset.getName(), preset.getEmail());
    }

    public Person() {
        this("Sinombre García Campos", null);
    }

    public Person(@NotNull String name, @Nullable String email) {
        if (email != null) {
            if (email.indexOf('@') == -1) throw new IllegalArgumentException("El email es incorrecto");
            this.email = email;
        }
        this.name = name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public String getEmail() {
        return this.email == null ? "" : this.email;
    }

    public abstract void createID();

    @Override
    public String toString() {
        return this.getName() + (this.getEmail().isEmpty() ? "" : " (" + this.getEmail() + ")");
    }
}
