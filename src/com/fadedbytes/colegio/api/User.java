package com.fadedbytes.colegio.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class User extends Person {

    private static final byte MAX_USUARIOS = 50;
    private static byte numUsers = 0;

    private byte ID;

    public User() {
        super();
        createID();
    }

    public User(@NotNull String name, @Nullable String email) {
        super(name, email);
        createID();
    }

    @Override
    public void createID() {
        this.ID = numUsers;
        numUsers++;
    }

    public byte getID() {
        return this.ID;
    }

    @NotNull
    public String accessSchoolData() {
        return "IES Pere Maria Orts i Bosch - Benidorm - Clases por la tarde";
    }

    @Override
    public String toString() {
        return this.ID + " - " + super.toString();
    }
}
