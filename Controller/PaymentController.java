package Controller;

/**
 * @author Janhavi Sonawane <a href="mailto:janhavi_1015@icloud.com">
 *         janhavi_1015@icloud.com</a>
 */

import Models.*;
import GUI.*;
import Database.*;

import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * PaymentController class controls the functionality for the fees view pages
 */
public class PaymentController implements ActionListener {
    
    private FeesView fees;
    private Fees fee;
    private Database db;
    private LandlordController landlord;
    private ManagerController manager;
  
    //Default constructor
    public PaymentController(){
        this.fee = new Fees();
        this.landlord = new LandlordController();
        this.manager = new ManagerController();
    }

    //Constructor with database connection
    public PaymentController(Database db) {
        this.db = db;

    }

    //getters and setters
    public ManagerController getManager() {
        return manager;
    }
    public FeesView getFeesView() {
        return fees;
    }
    public void setFees(FeesView fees) {
        this.fees = fees;
    }
    public Fees getFee() {
        return fee;
    }
    public void setFee(Fees fee) {
        this.fee = fee;
    }
    public Database getDb() {
        return db;
    }
    public void setDb(Database db) {
        this.db = db;
    }
    public LandlordController getLandlord() {
        return landlord;
    }
    public void setLandlord(LandlordController landlord) {
        this.landlord = landlord;
    }
    public void setManager(ManagerController manager) {
        this.manager = manager;
    }

    //enable fees page depending on user type
    public void enableView(boolean isMgrPlaceholder) {
        fees = new FeesView(isMgrPlaceholder); 
        fees.addPayFeeListener(this);
        fees.addSaveListener(this);
        fees.addSelectListener(this);
    }

    //Handle events on fees page
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.fees != null)
        {
            db.initializeConnection();
            if(!fees.getPID().equals(""))
            {
                if(e.getSource().equals(fees.getPayFeesButton())) //landlord pays the fees
                    {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
                        LocalDateTime current = LocalDateTime.now();
                        LocalDateTime end = current.plusMonths(db.getPeriod(Integer.valueOf(fees.getPID())));
                        db.updatePaidProperty(Integer.valueOf(fees.getPID()), dtf.format(current).toString(), dtf.format(end).toString());
                        db.updateFeeStatus(Integer.valueOf(fees.getPID()));
                    }
            }
            if(!fees.getFee().equals("") && !fees.getPID().equals("")) 
            {
                if(e.getSource().equals(fees.getSaveButton())) //manager edits the fees
                {
                    String periodString = fees.getPeriod().substring(0, fees.getPeriod().indexOf(' '));
                    int period = Integer.valueOf(periodString);
                    db.updatePeriodStatus(Integer.valueOf(fees.getPID()), Double.valueOf(fees.getFee()), period);
                }
            }

            if(e.getSource().equals(fees.getSelectButton())) //landlord selects a property to pay fees for
            {
                //update property start and end date
                fees.setFee(String.valueOf(db.getFees(Integer.valueOf(fees.getPID()))));
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
                LocalDateTime current = LocalDateTime.now();
                String line = dtf.format(current).toString();
                String day = line.substring(line.indexOf('-') + 4, line.indexOf('-')+6);
                String month = line.substring(line.indexOf('-') + 1, line.indexOf('-') + 3);
                String year = line.substring(0, line.indexOf('-'));
                fees.setStartD(day);
                fees.setStartM(month);
                fees.setStartY(year);
                LocalDateTime end = current.plusMonths(db.getPeriod(Integer.valueOf(fees.getPID())));
                line = dtf.format(end).toString();
                day = line.substring(line.indexOf('-') + 4, line.indexOf('-')+6);
                month = line.substring(line.indexOf('-') + 1, line.indexOf('-') + 3);
                year = line.substring(0, line.indexOf('-'));
                fees.setEndD(day);
                fees.setEndM(month);
                fees.setEndY(year);
            }
            
        }  
    }
}
