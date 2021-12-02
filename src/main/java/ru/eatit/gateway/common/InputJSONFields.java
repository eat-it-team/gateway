package ru.eatit.gateway.common;

public enum InputJSONFields {

    TEXT("TEXT", "текст для анализа" ),
    USE_EMOTION_ANALYSE("USE_EMOTION_ANALYSE", "Нужен анализ эмоций" ),
    USE_MAT_ANALYSE("USE_MAT_ANALYSE", "Нужен анализ на использование мата" );
    InputJSONFields(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static InputJSONFields getField(Object name) {
       for (InputJSONFields value: InputJSONFields.values()) {
           if (value.getName().equals(name.toString())) {
               return value;
           }
        }
       return null;
    }
}
