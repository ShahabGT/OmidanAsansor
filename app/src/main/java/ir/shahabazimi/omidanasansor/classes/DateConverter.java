package ir.shahabazimi.omidanasansor.classes;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;


public class DateConverter {

    private int day, month, year;
    private int jYear, jMonth, jDay;
    private int gYear, gMonth, gDay;
    private int leap, march;
    private int JG2JD(int year, int month, int day, int J1G0) {
        int jd = (1461 * (year + 4800 + (month - 14) / 12)) / 4
                + (367 * (month - 2 - 12 * ((month - 14) / 12))) / 12
                - (3 * ((year + 4900 + (month - 14) / 12) / 100)) / 4 + day
                - 32075;
        if (J1G0 == 0) {
            jd = jd - (year + 100100 + (month - 8) / 6) / 100 * 3 / 4 + 752;
        }
        return jd;
    }

    private void JD2JG(int JD, int J1G0) {
        int i, j;
        j = 4 * JD + 139361631;
        if (J1G0 == 0) {
            j = j + (4 * JD + 183187720) / 146097 * 3 / 4 * 4 - 3908;
        }
        i = (j % 1461) / 4 * 5 + 308;
        gDay = (i % 153) / 5 + 1;
        gMonth = ((i / 153) % 12) + 1;
        gYear = j / 1461 - 100100 + (8 - gMonth) / 6;
    }

    private void JD2Jal(int JDN) {
        JD2JG(JDN, 0);
        jYear = gYear - 621;
        JalCal(jYear);
        int JDN1F = JG2JD(gYear, 3, march, 0);
        int k     = JDN - JDN1F;
        if (k >= 0) {
            if (k <= 185) {
                jMonth = 1 + k / 31;
                jDay = (k % 31) + 1;
                return;
            } else {
                k = k - 186;
            }
        } else {
            jYear = jYear - 1;
            k = k + 179;
            if (leap == 1) {
                k = k + 1;
            }
        }
        jMonth = 7 + k / 30;
        jDay = (k % 30) + 1;
    }

    private int Jal2JD(int jY, int jM, int jD) {
        JalCal(jY);
        return JG2JD(gYear, 3, march, 1) + (jM - 1) * 31 - jM / 7 * (jM - 7) + jD - 1;
    }

    private void JalCal(int jY) {
        march = 0;
        leap = 0;
        int[] breaks = {-61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181, 1210,
                1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178};
        gYear = jY + 621;
        int leapJ = -14;
        int jp    = breaks[0];
        int jump  = 0;
        for (int j = 1; j <= 19; j++) {
            int jm = breaks[j];
            jump = jm - jp;
            if (jY < jm) {
                int N = jY - jp;
                leapJ = leapJ + N / 33 * 8 + (N % 33 + 3) / 4;
                if ((jump % 33) == 4 && (jump - N) == 4) {
                    leapJ = leapJ + 1;
                }
                int leapG = (gYear / 4) - (gYear / 100 + 1) * 3 / 4 - 150;
                march = 20 + leapJ - leapG;
                if ((jump - N) < 6) {
                    N = N - jump + (jump + 4) / 33 * 33;
                }
                leap = ((((N + 1) % 33) - 1) % 4);
                if (leap == -1) {
                    leap = 4;
                }
                break;
            }
            leapJ = leapJ + jump / 33 * 8 + (jump % 33) / 4;
            jp = jm;
        }
    }


    @SuppressLint("DefaultLocale")
    @Override
    @NonNull
    public String toString() {
        return String.format("%04d/%02d/%02d", getYear(), getMonth(), getDay());
    }


    public void gregorianToPersian(int year, int month, int day) {
        int jd = JG2JD(year, month, day, 0);
        JD2Jal(jd);
        this.year = jYear;
        this.month = jMonth;
        this.day = jDay;
    }

    public void persianToGregorian(int year, int month, int day) {
        int jd = Jal2JD(year, month, day);
        JD2JG(jd, 0);
        this.year = gYear;
        this.month = gMonth;
        this.day = gDay;
    }


    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

}