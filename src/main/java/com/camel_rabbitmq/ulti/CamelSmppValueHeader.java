package com.camel_rabbitmq.ulti;

public class CamelSmppValueHeader {

    //    TON (Type Of Number) values.
    public static final byte SMPP_TON_UNK = 0x00;
    public static final byte SMPP_TON_INTL = 0x01;
    public static final byte SMPP_TON_NATNL = 0x02;
    public static final byte SMPP_TON_NWSPEC = 0x03;
    public static final byte SMPP_TON_SBSCR = 0x04;
    public static final byte SMPP_TON_ALNUM = 0x05;
    public static final byte SMPP_TON_ABBREV = 0x06;

    //    NPI (Numbering Plan Indicator) values.
    public static final byte SMPP_NPI_UNK = 0x00;    // Unknown
    public static final byte SMPP_NPI_ISDN = 0x01;   // ISDN (E163/E164)
    public static final byte SMPP_NPI_DATA = 0x03;   // Data (X.121)
    public static final byte SMPP_NPI_TELEX = 0x04;  // Telex (F.69)
    public static final byte SMPP_NPI_LNDMBL = 0x06; // Land Mobile (E.212)
    public static final byte SMPP_NPI_NATNL = 0x08;  // National
    public static final byte SMPP_NPI_PRVT = 0x09;   // Private
    public static final byte SMPP_NPI_ERMES = 0x0A;  // ERMES
    public static final byte SMPP_NPI_IP = 0x0E;     // IPv4
    public static final byte SMPP_NPI_WAP = 0x12;    // WAP
}
