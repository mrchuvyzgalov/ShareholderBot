package ru.sbrf.shareholderbot.company;


public enum Company {
    SBER, VTB, AFLT, Yandex, MAIL, TCSG;

    public String getNameOfCompany() {
        switch(this) {
            case SBER: return "Сбер Банк";
            case VTB: return "Банк ВТБ";
            case AFLT: return "Аэрофлот";
            case Yandex: return "Yandex";
            case MAIL: return "MAIL.RU";
            case TCSG: return "TCS Group";
            default: return null;
        }
    }

    public String getFigi() {
        switch (this) {
            case SBER: return "BBG004730N88";
            case VTB: return "BBG004730ZJ9";
            case AFLT: return "BBG004S683W7";
            case Yandex: return "BBG006L8G4H1";
            case MAIL: return "BBG00178PGX3";
            case TCSG: return "BBG00QPYJ5H0";
            default: return null;
        }
    }
}
