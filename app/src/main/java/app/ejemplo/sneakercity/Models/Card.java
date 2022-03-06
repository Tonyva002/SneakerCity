package app.ejemplo.sneakercity.Models;

import app.ejemplo.sneakercity.Helpes.UtilsHelper;
import com.google.firebase.database.IgnoreExtraProperties;

import org.parceler.Parcel;

import java.util.UUID;

@Parcel
@IgnoreExtraProperties
public class Card {

    private String cardNumber, expirationDate, securityCode, idCard, namePrintCard;


    public Card(){

    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getNamePrintCard() {
        return namePrintCard;
    }

    public void setNamePrintCard(String namePrintCard) {
        this.namePrintCard = namePrintCard;
    }



    public void saveCardInformation(boolean isNew){
        String randomId = idCard;
        if (isNew){
            randomId = UUID.randomUUID().toString();
        }
        saveCardInformationDB(randomId);
    }

    private void saveCardInformationDB(String newId) {
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("card").child(newId).child("cardNumber").setValue(cardNumber);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("card").child(newId).child("expirationDate").setValue(expirationDate);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("card").child(newId).child("securityCode").setValue(securityCode);
        UtilsHelper.getDatabase().child(UtilsHelper.getUserId()).child("card").child(newId).child("namePrintCard").setValue(namePrintCard);
    }
}
