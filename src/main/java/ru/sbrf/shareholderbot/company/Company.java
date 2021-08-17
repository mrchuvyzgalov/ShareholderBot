package ru.sbrf.shareholderbot.company;


public enum Company {
    SBER, VTB, Tinkoff, Yandex;

    public String getNameOfCompany() {
        switch(this){
            case SBER: return "СБЕР";
            case VTB: return "ВТБ";
            case Tinkoff: return "Тинькофф";
            case Yandex: return "Яндекс";
            default: return null;
        }
    }
}
