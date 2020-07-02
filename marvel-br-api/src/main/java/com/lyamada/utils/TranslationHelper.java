package com.lyamada.utils;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;

public class TranslationHelper {
    public static String translate(String sentence) {
        if (sentence == "" || sentence == null) {
            return sentence;
        }
        Translate translate = TranslateOptions.newBuilder().build().getService();
        return translate.translate(sentence,TranslateOption.sourceLanguage("en"),
                TranslateOption.targetLanguage("pt-br")).getTranslatedText();
    }
}
