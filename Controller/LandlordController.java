package Controller;

/**
 * @author Janhavi Sonawane <a href="mailto:janhavi_1015@icloud.com">
 *         janhavi_1015@icloud.com</a>
 */

import Models.*;
import GUI.*;
import Database.Database;

import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * LandlordController class controls the functionality for the landlord's main page
 */
public class LandlordController implements ActionListener{
    private Landlord landlord;
    private LandlordView landlordV;
    private PaymentController pay;
    private PropertyController prop;
    private RegisterController regProp;
    private SearchController search;
    private int Id;
    private Database db;

    //Default constructor to create instances of member variables
    public LandlordController()
    {
        this.landlord = new Landlord();
        this.pay = new PaymentController();
        this.prop = new PropertyController();
        this.regProp = new RegisterController();
        this.search = new SearchController();
        this.Id = -1;
    }

    //Landlord constructor to initialize database connection
    public LandlordController(Database db){
        this.db = db;
        this.landlord = new Landlord();
        this.pay = new PaymentController(db);
        this.prop = new PropertyController(db);
        this.regProp = new RegisterController(db);
        this.search = new SearchController(db);
    }

    /**
     * Handle events on the landlord main page
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource().equals(landlordV.getSearch())) //landlord clicks search button
        {
            landlordV.destroyFrame();
            search.enableLlrdView();
            search.getView().addBackButtonListener(this);
        }

        if(e.getSource().equals(landlordV.getRegister())) //landlord clicks register button
        {
            landlordV.destroyFrame();
            regProp.enableView(this.landlordV);
            this.regProp.getCreateProp().addBackPropertyListener(this);
        }

        if(e.getSource().equals(landlordV.getUpdate())) //landlord clicks update button
        {
            landlordV.destroyFrame();
            prop.enableView();
            prop.getEditView().turnOn();
            prop.getEditView().addBackListener(this);
            prop.getEditView().addSaveListener(this);
        }

        if(e.getSource().equals(landlordV.getPay())) //landlord clicks pay fees button
        {
            landlordV.destroyFrame();
            pay.enableView(false);
            
            pay.getFeesView().turnOnForLandlord();
            pay.getFeesView().addBackListener(this);
            pay.getFeesView().addPayFeeListener(this);
        }
        if(regProp.getCreateProp() != null) //landlord clicks the back button on create property view
        {
            if(e.getSource().equals(regProp.getCreateProp().getBack()))
            {
                regProp.getCreateProp().destroyFrame();
                landlordV.turnOn();
            }
        }

        if(prop.getEditView() != null) //landlord clicks the back button on the edit property view
        {
            if(e.getSource().equals(prop.getEditView().getBackButton()))
            {
                
                prop.getEditView().destroyFrame();
                landlordV.turnOn();
            }
        }

        if(prop.getEditView() != null) //landlord clicks save on the edit property view
        {
            if(e.getSource().equals(prop.getEditView().getSaveButton()))
            {
                if(!prop.getEditView().getPropertyIDStringInput().equals("")) //there are no empty fields
                {
                    prop.getEditView().showDialog();
                    prop.getEditView().destroyFrame();
                    landlordV.turnOn();
                }
                else{
                    prop.getEditView().showErrorDialog();
                }
            }
        }

        if(pay.getFeesView() != null) //landlord clicks the back button on the pay fees view
        {
            if(e.getSource().equals(pay.getFeesView().getBackButton()))
            {
                pay.getFeesView().destroyFrameForLandlord();
                landlordV.turnOn();
            }
        }

        if(pay.getFeesView() != null) 
        {
            if(e.getSource().equals(pay.getFeesView().getPayFeesButton())) //landlord clicks the pay fees button on the pay fees view
            {
                //landlord enters pay information
                if(!pay.getFeesView().getPID().equals("") && !pay.getFeesView().getFName().equals("") && !pay.getFeesView().getLName().equals("")
                && !pay.getFeesView().getCountry().equals("") && !pay.getFeesView().getPCode().equals("") && !pay.getFeesView().getCCN().equals("") 
                && !pay.getFeesView().getMMExp().equals("") && !pay.getFeesView().getYYExp().equals("") && !pay.getFeesView().getCVV().equals(""))
                {
                    pay.getFeesView().showDialogLandlord();
                    pay.getFeesView().destroyFrameForLandlord();
                    landlordV.turnOn();
                }
                else
                {
                    pay.getFeesView().showErrorDialogLandlord();
                }
            }
        }

        if(search.getView() != null) //landlord clicks the back button on the search view
        {
            if(e.getSource().equals(search.getView().getBackButton()))
            {
                search.getView().destroyFrameForLandLord();
                landlordV.turnOn();
            }
        }
    }

    //setter for landlord id within register controller
    public void setRegisterControllerId(int id)
    {
        this.regProp.setLandlordID(id);
    }

     //setter for landlord id within search controller
    public void setSearchControllerID(int id)
    {
        this.search.setLandlordID(id);
    }
    
    //getter for landlord
    public Landlord getLandlord(){
        return this.landlord;
    }

    //setter for landlord
    public void setLandlord(Landlord ld){
        this.landlord = ld;
    }

    //getter for landlord view 
    public LandlordView getLandlordView(){
        return this.landlordV;
    }
    
    //setter for landlord view
    public void setLandLordV(LandlordView ldView){
        this.landlordV = ldView;
    }

    //getter for payment controller
    public PaymentController getPay(){
        return this.pay;
    }

    //setter for payment controller
    public void setPay(PaymentController payC){
        this.pay = payC;
    }

    //getter for property controller
    public PropertyController getProp(){
        return this.prop;
    }

    //setter for property controller
    public void setProp(PropertyController propC){
        this.prop = propC;
    }

    //getter for register controller
    public RegisterController getRegProp(){
        return this.regProp;
    }

    //setter for register controller
    public void setRegProp(RegisterController regC){
        this.regProp = regC;
    }

    public SearchController getSearch(){
        return this.search;
    }

    public void enableView(ActionListener logoutListener, int id, String type)
    {
        this.Id = id;
        landlordV = new LandlordView();
        prop = new PropertyController(this.db,this.Id, type);
        this.getLandlordView().addLogoutListener(logoutListener);
        this.getLandlordView().addRegisterPropertyListener(this);
        this.getLandlordView().addUpdatePropertyListener(this);
        this.getLandlordView().addPayFeeListener(this);
        this.getLandlordView().addSearchListener(this);
    }
}