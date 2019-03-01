package com.myapp.apangcatan.spendingdiary.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

@Entity(nameInDb = "expense")
public class ExpenseModel {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "description")
    private String description;
    @Property(nameInDb = "item")
    private String item;
    @Property(nameInDb = "date")
    private String date;
    @Property(nameInDb = "amount")
    private String amount;
    @Property(nameInDb = "budgetPlanId")
    private Long budgetPlanId;
    @Generated(hash = 1422523410)
    public ExpenseModel(Long id, String description, String item, String date,
            String amount, Long budgetPlanId) {
        this.id = id;
        this.description = description;
        this.item = item;
        this.date = date;
        this.amount = amount;
        this.budgetPlanId = budgetPlanId;
    }
    @Generated(hash = 403675465)
    public ExpenseModel() {
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
    public String getItem() {
        return this.item;
    }
    public void setItem(String item) {
        this.item = item;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getAmount() {
        return this.amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public Long getBudgetPlanId() {
        return this.budgetPlanId;
    }
    public void setBudgetPlanId(Long budgetPlanId) {
        this.budgetPlanId = budgetPlanId;
    }

}
