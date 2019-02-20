package com.myapp.apangcatan.spendingdiary.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "expense")
public class ExpenseModel {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "description")
    private String description;
    @Property(nameInDb = "item")
    private String item;
    @Property(nameInDb = "type")
    private String type;
    @Property(nameInDb = "date")
    private String date;
    @Property(nameInDb = "amount")
    private String amount;

    @Keep
    public ExpenseModel(String description, String item, String type,
                        String date, String amount) {
        this.description = description;
        this.item = item;
        this.type = type;
        this.date = date;
        this.amount = amount;
    }

    @Keep
    public ExpenseModel() {
    }

    @Generated(hash = 1095523929)
    public ExpenseModel(Long id, String description, String item, String type,
            String date, String amount) {
        this.id = id;
        this.description = description;
        this.item = item;
        this.type = type;
        this.date = date;
        this.amount = amount;
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
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

}
