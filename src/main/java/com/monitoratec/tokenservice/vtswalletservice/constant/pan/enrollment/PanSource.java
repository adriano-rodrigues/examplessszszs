package com.monitoratec.tokenservice.vtswalletservice.constant.pan.enrollment;

/**
 * ONFILE – The wallet provider already has the PAN data for the customer in their records.
 * MANUALLYENTERED – The customer is expected to type the card number or capture a picture of the card.
 * ISSUER_PUSH_PROVISION – The PAN data was provided by the issuer of the PAN.
 * TOKEN_FOR_TOKEN – Token was used for source of the PAN data.
 */
public enum PanSource {

    ONFILE,
    MANUALLYENTERED,
    ISSUER_PUSH_PROVISION,
    TOKEN_FOR_TOKEN,
}


