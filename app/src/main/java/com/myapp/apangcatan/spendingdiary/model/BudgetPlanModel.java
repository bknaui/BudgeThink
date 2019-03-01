package com.myapp.apangcatan.spendingdiary.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "budget_plan")
public class BudgetPlanModel {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "description")
    private String description;
    @Property(nameInDb = "from_date")
    private String fromDate;
    @Property(nameInDb = "to_date")
    private String toDate;
    @Property(nameInDb = "amount")
    private String amount;
    @Property(nameInDb = "selected")
    private boolean selected = false;
    @Generated(hash = 1124457407)
    public BudgetPlanModel(Long id, String description, String fromDate,
            String toDate, String amount, boolean selected) {
        this.id = id;
        this.description = description;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.amount = amount;
        this.selected = selected;
    }
    @Generated(hash = 1596899083)
    public BudgetPlanModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getFromDate() {
        return this.fromDate;
    }
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }
    public String getToDate() {
        return this.toDate;
    }
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
    public String getAmount() {
        return this.amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public boolean getSelected() {
        return this.selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
