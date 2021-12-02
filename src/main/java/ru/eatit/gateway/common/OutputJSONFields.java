package ru.eatit.gateway.common;

public enum OutputJSONFields {
    EMOTION_ANALYSE_RESULT("EMOTION_ANALYSE_RESULT", "Результат анализа эмоций" ),
    MAT_ANALYSE_RESULT("MAT_ANALYSE_RESULT", "Результат анализа на использование мата" ),
    ERROR_RESULT("ERROR_RESULT", "Отправляется в случае ошибки");
    OutputJSONFields(String name, String description) {
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

    public static OutputJSONFields getField(Object name) {
        for (OutputJSONFields value: OutputJSONFields.values()) {
            if (value.getName().equals(name.toString())) {
                return value;
            }
        }
        return null;
    }
}
