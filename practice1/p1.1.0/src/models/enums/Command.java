package models.enums;
/*
Explanation:
- for handling regexes and commands we need to define an interface.
- we have different menus that each one has different commands(so different regexes).
- but after all they are all commands and we can write some part of their code once here (:
 */

import java.util.regex.Matcher;

public interface Command {
    final String pattern = null;
    public Matcher getMatcher(String input);
}