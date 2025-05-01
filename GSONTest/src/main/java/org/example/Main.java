package org.example;

import com.google.gson.Gson;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        UserSimple User = new UserSimple("mahdi" , 18);
        Gson gson = new Gson();
        String json = gson.toJson(User);
        System.out.println(json);
    }
}