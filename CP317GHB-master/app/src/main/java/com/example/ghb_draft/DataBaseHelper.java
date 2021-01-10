package com.example.ghb_draft;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

    public class DataBaseHelper extends SQLiteOpenHelper {
        // Accounts table in SQLite Database, column names
        public static final String ACCOUNTS_TABLE = "ACCOUNTS_TABLE";
        public static final String ACCOUNT_EMAIL = "ACCOUNT_EMAIL";
        public static final String ACCOUNT_BALANCE = "ACCOUNT_BALANCE";
        public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";
        public static final String ACCOUNT_ID = "ACCOUNT_ID";
        public static final String STUDENT_NUMBER = "STUDENT_NUMBER";
        // Customer table in SQLite Database, column names
        public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
        public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
        public static final String COLUMN_CUSTOMER_EMAIL = "CUSTOMER_EMAIL";
        // User table in SQLite Database, column names
        public static final String USER_TABLE = "USER_TABLE";
        public static final String USER_ID = "USER_ID";
        public static final String USER_EMAIL = "USER_EMAIL";
        public static final String USER_PHONE_NUMBER = "USER_PHONE_NUMBER";
        public static final String USER_FULL_NAME = "USER_FULL_NAME";
        public static final String USER_PASSWORD = "USER_PASSWORD";
        // E-transfers table in SQLite Database, column names
        public static final String ETRANSFERS_TABLE = "ETRANSFERS_TABLE";
        public static final String ETRANSFER_OUTGOING = "ETRANSFER_OUTGOING";
        public static final String ETRANSFER_RECEIVING = "ETRANSFER_RECEIVING";
        public static final String ETRANSFER_AMOUNT = "ETRANSFER_AMOUNT";

        // Active admin identifier defaulted to false
        public static boolean ACTIVE_ADMIN = false;
        // Initializing active user, outgoing and receiving accounts for further functions
        public static String ACTIVE_USER = "ACTIVE_USER";
        public static Accounts OUTGOING;
        public static Accounts RECIEVING;
        public DataBaseHelper(@Nullable Context context) {

            super(context, "customer.db", null, 1);
        }

        // this is called the first time a database is accessed.
        @Override
        public void onCreate(SQLiteDatabase db) {
            // Creation of class tables in SQLite Database
            String createTableStatement = "CREATE TABLE " + CUSTOMER_TABLE + " (" + USER_ID + " TEXT, " + COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_CUSTOMER_EMAIL + " TEXT)";
            String createTableStatement2 = "CREATE TABLE " + USER_TABLE + " (" + USER_FULL_NAME + " TEXT, " + USER_PHONE_NUMBER + " TEXT, " + USER_EMAIL + " TEXT, " + USER_PASSWORD + " TEXT)";
            String createTableStatement3 = "CREATE TABLE " + ACCOUNTS_TABLE + " (" + ACCOUNT_EMAIL + " TEXT, " + ACCOUNT_BALANCE + " FLOAT, " + ACCOUNT_TYPE + " TEXT, " + STUDENT_NUMBER + " INTEGER, " + ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT)";
            String createTableStatement4 = "CREATE TABLE " + ETRANSFERS_TABLE + " (" + ETRANSFER_OUTGOING + " TEXT, " + ETRANSFER_RECEIVING + " TEXT, " + ETRANSFER_AMOUNT + " FLOAT)";
            db.execSQL(createTableStatement4);
            db.execSQL(createTableStatement);
            db.execSQL(createTableStatement2);
            db.execSQL(createTableStatement3);
        }

        // this is called if the database version number changes.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
        // Add contact to contacts table on database
        public boolean addOne(CustomerModel customerModel){

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(USER_ID, customerModel.getUser());
            cv.put(COLUMN_CUSTOMER_NAME, customerModel.getName());
            cv.put(COLUMN_CUSTOMER_EMAIL, customerModel.getEmail());


            long insert = db.insert(CUSTOMER_TABLE, null, cv);

            if (insert == -1){
                return false;
            }
            else {
                return true;
            }


        }
        // Checks if email exists in the database users table
        public boolean isEmail(String email) {
            SQLiteDatabase db = getWritableDatabase();
            String selectString = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_EMAIL + " = ?";
            Cursor cursor = db.rawQuery(selectString, new String[] {email});

            boolean hasObject = false;
            if (cursor.moveToFirst()) {
                hasObject = true;

                int count = 0;
                while (cursor.moveToNext()) {
                    count++;
                }
                Log.d(null, String.format("%d records found", count));

            }
            cursor.close();
            db.close();
            return  hasObject;


        }
        // Adds new registered user to the database user table
        public boolean addUser(UserModel userModel) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv2 = new ContentValues();

            cv2.put(USER_FULL_NAME, userModel.getFullName());
            cv2.put(USER_PHONE_NUMBER, userModel.getUserPhone());
            cv2.put(USER_EMAIL, userModel.getUserEmail());
            cv2.put(USER_PASSWORD, userModel.getUserPassword());

            long insert = db.insert(USER_TABLE, null, cv2);

            if (insert == -1){
                return false;
            }
            else {
                return true;
            }
        }
        // Adds new banking account to the database accounts table
        public boolean addAccount(Accounts account) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv3 = new ContentValues();
            cv3.put(ACCOUNT_EMAIL, account.getEmail());
            cv3.put(ACCOUNT_BALANCE, account.getBalance());
            cv3.put(ACCOUNT_TYPE, account.getType());
            cv3.put(STUDENT_NUMBER, account.getStudentNumber());
            long insert = db.insert(ACCOUNTS_TABLE, null, cv3);
            if (insert == -1){
                return false;
            }else{
                return true;
            }
        }
        // Adds new e-transfer class to the database e-transfers table
        public boolean addEtransfer(Etransfers etransfer) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv4 = new ContentValues();
            cv4.put(ETRANSFER_OUTGOING, etransfer.getOutgoingEmail());
            cv4.put(ETRANSFER_RECEIVING, etransfer.getReceivingEmail());
            cv4.put(ETRANSFER_AMOUNT, etransfer.getAmount());
            long insert = db.insert(ETRANSFERS_TABLE, null, cv4);
            if (insert == -1){
                return false;
            }else{
                return true;
            }
        }
        // Deletes the active user's account from the database user table
        public boolean deleteOne(){
            // find customerModel in the database. If it found, delete it and return true.
            // if it is not found, return false

            SQLiteDatabase db = this.getWritableDatabase();
            String queryString = "DELETE FROM " + USER_TABLE + " WHERE " + USER_EMAIL + "= ?";
            Cursor cursor = db.rawQuery(queryString, new String[] {ACTIVE_USER});

            if (cursor.moveToFirst()){
                return true;
            }
            else{
                return false;
            }
        }
        // Deletes a selected contact from the customer table
        public boolean deleteOne(CustomerModel customerModel){
            // find customerModel in the database. If it found, delete it and return true.
            // if it is not found, return false

            SQLiteDatabase db = this.getWritableDatabase();
            String queryString = "DELETE FROM " + CUSTOMER_TABLE + " WHERE " + COLUMN_CUSTOMER_EMAIL + " = ?";
            Cursor cursor =  db.rawQuery(queryString, new String[]{customerModel.getEmail()});

            if (cursor.moveToFirst()){
                return true;
            }
            else{
                return false;
            }
        }
        // Deletes a selected banking account from the database accounts table
        public boolean deleteAccount(Accounts account){

            SQLiteDatabase db = this.getWritableDatabase();
            String queryString = "DELETE FROM " + ACCOUNTS_TABLE + " WHERE " + ACCOUNT_ID + " = ?";
            Cursor cursor =  db.rawQuery(queryString, new String[]{account.getId().toString()});

            if (cursor.moveToFirst()){
                return true;
            }
            else{
                return false;
            }

        }

        // Gets all contacts with the ID matching the active user, searching the database customer table which contains all contacts
        public List<CustomerModel> getEveryone() {
            List<CustomerModel> returnList = new ArrayList<>();

            // get data from the database
            SQLiteDatabase db = this.getReadableDatabase();
            String queryString = "SELECT * FROM " + CUSTOMER_TABLE + " WHERE " + USER_ID + " = ?";



            Cursor cursor = db.rawQuery(queryString, new String[] {ACTIVE_USER});
            if (cursor.moveToFirst()) {
                // loop through the cursor (result set) and create new customer objects. Put them into the return list.
                do {
                    String userID = cursor.getString(0);
                    String customerName = cursor.getString(1);
                    String customerEmail = cursor.getString(2);
                    CustomerModel newCustomer = new CustomerModel(userID, customerName, customerEmail);
                    returnList.add(newCustomer);

                } while (cursor.moveToNext());
            }
            else {
                // failure. do not add anything to the list
            }

            // close both the cursor and the db when done
            cursor.close();
            db.close();
            return returnList;
        }
        // Gets all e-transfers with outgoing account matching the active user in database, grabs from e-transfers table
        public List<Etransfers> getEtransfers1() {
            List<Etransfers> returnList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String queryString = "SELECT * FROM " + ETRANSFERS_TABLE + " WHERE " + ETRANSFER_OUTGOING + " = ?";
            Cursor cursor = db.rawQuery(queryString, new String[] {ACTIVE_USER});
            if (cursor.moveToFirst()) {
                do {
                    String outgoingEmail = cursor.getString(0);
                    String receivingEmail = cursor.getString(1);
                    Float transferAmount = cursor.getFloat(2);
                    Etransfers etransfer = new Etransfers(outgoingEmail, receivingEmail, transferAmount);
                    returnList.add(etransfer);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return returnList;
        }
        // Gets all e-transfers with receiving account mathing the active user in database, grabs from the e-transfers table
        public List<Etransfers> getEtransfers2() {
            List<Etransfers> returnList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String queryString = "SELECT * FROM " + ETRANSFERS_TABLE + " WHERE " + ETRANSFER_RECEIVING + " = ?";
            Cursor cursor = db.rawQuery(queryString, new String[] {ACTIVE_USER});
            if (cursor.moveToFirst()) {
                do {
                    String outgoingEmail = cursor.getString(0);
                    String receivingEmail = cursor.getString(1);
                    Float transferAmount = cursor.getFloat(2);
                    Etransfers etransfer = new Etransfers(outgoingEmail, receivingEmail, transferAmount);
                    returnList.add(etransfer);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return returnList;
        }
        // Gets all bank accounts where the account email is equal to the active user in database, grabs from the accounts table
        public List<Accounts> getAccounts() {
            List<Accounts> returnList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String queryString = "SELECT * FROM " + ACCOUNTS_TABLE + " WHERE " + ACCOUNT_EMAIL + " = ?";
            Cursor cursor = db.rawQuery(queryString, new String[] {ACTIVE_USER});
            if (cursor.moveToFirst()) {
                do {
                    String accountEmail = cursor.getString(0);
                    Float accountBalance = cursor.getFloat(1);
                    String accountType = cursor.getString(2);
                    Integer studentNumber = cursor.getInt(3);
                    Integer accountId = cursor.getInt(4);
                    Accounts account = new Accounts(accountEmail, accountBalance, accountType, studentNumber, accountId);
                    returnList.add(account);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return returnList;
        }
        // Gets all banking account from selected email of user in database, grabs from the accounts table
        public List<Accounts> getReceivingAccounts(String email) {
            List<Accounts> returnList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String queryString = "SELECT * FROM " + ACCOUNTS_TABLE + " WHERE " + ACCOUNT_EMAIL + " = ?";
            Cursor cursor = db.rawQuery(queryString, new String[] {email});
            if (cursor.moveToFirst()) {
                do {
                    String accountEmail = cursor.getString(0);
                    Float accountBalance = cursor.getFloat(1);
                    String accountType = cursor.getString(2);
                    Integer studentNumber = cursor.getInt(3);
                    Integer accountId = cursor.getInt(4);
                    Accounts account = new Accounts(accountEmail, accountBalance, accountType, studentNumber, accountId);
                    returnList.add(account);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return returnList;
        }
        // Gets all banking accounts eligible as outgoing where account email equals active user in database, grabs from accounts table
        public List<Accounts> getOutgoingAccounts() {
            List<Accounts> returnList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String queryString = "SELECT * FROM " + ACCOUNTS_TABLE + " WHERE " + ACCOUNT_EMAIL + " = ?";
            Cursor cursor = db.rawQuery(queryString, new String[] {ACTIVE_USER});
            if (cursor.moveToFirst()) {
                do {
                    String accountType = cursor.getString(2);
                    if (!(accountType.equals("One Card"))) {
                        String accountEmail = cursor.getString(0);
                        Float accountBalance = cursor.getFloat(1);
                        Integer studentNumber = cursor.getInt(3);
                        Integer accountId = cursor.getInt(4);
                        Accounts account = new Accounts(accountEmail, accountBalance, accountType, studentNumber, accountId);
                        returnList.add(account);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return returnList;
        }
        // Grabs outgoing and receiving accounts
        public List<Accounts> getSelectedAccounts() {
            List<Accounts> returnList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Accounts outgoing = getOUTGOING();
            Accounts receiving = getRECIEVING();
            returnList.add(outgoing);
            returnList.add(receiving);
            db.close();
            return returnList;
        }
        // Checks if the email is recognized as admin
        public void isAdmin (String email) {
            ACTIVE_USER = email;

            if (email.equals("Admin")){
                ACTIVE_ADMIN = true;
            } else {
                ACTIVE_ADMIN = false;
            }
        }

        public boolean Admin() {
            return ACTIVE_ADMIN;
        }
        // Checks if email and password are valid in the database
        public boolean isValidEmailAndPassword(String email, String password) {
            // get data from the database
            String queryString = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_EMAIL + " = ?";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(queryString, new String[] {email});

            boolean hasObject = false;
            if (cursor.moveToNext()) {
                String pw = cursor.getString(3);
                if (pw.equals(password)) {
                    hasObject = true;
                }

            }
            //special important line
            ACTIVE_USER = email;
            ACTIVE_ADMIN = false;
            // close both the cursor and the db when done
            cursor.close();
            db.close();
            return hasObject;
        }
        // Checks if email is recognized in the database under the user table user email column
        public boolean isValidEmail(String email) {
            String query = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_EMAIL + " = ?";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, new String[] {email});
            boolean hasObject = false;
            if (cursor.moveToNext()) {
                hasObject = true;
            } else {

            }

            db.close();
            cursor.close();
            return hasObject;
        }
        // Checks if the active user has a saved one card account in the database
        public boolean findStudentAccount(){
            String query = "SELECT * FROM " + ACCOUNTS_TABLE + " WHERE " + ACCOUNT_EMAIL + " =?";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, new String[] {ACTIVE_USER});
            boolean hasObject = true;
            if (cursor.moveToFirst()) {
                do {
                    String accountType = cursor.getString(2);
                    if (accountType.equals("One Card")){
                        hasObject = false;
                    }else{
                    }

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return hasObject;
        }
        // Gets all info on the user class where the email is equal to the active user, grabs info from database user table
        public ArrayList<String> grabInfo() {

            String userString = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_EMAIL + " = ?";

            ArrayList<String> list = new ArrayList<>();

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery(userString, new String[]{ACTIVE_USER});

            if (cursor.moveToFirst()) {
                // loop through the cursor (result set) and create new customer objects. Put them into the return list.

                String pw = cursor.getString(3);
                String name = cursor.getString(0);
                String phone = cursor.getString(1);

                list.add(name);
                list.add(phone);
                list.add(ACTIVE_USER);
                list.add(pw);
            }
            else {
                // failure. do not add anything to the list
            }
            cursor.close();
            db.close();
            return list;

        }
        // Updates name phone and password in the database user table where user email equals active user
        public boolean updateSettings(String name, String phone, String password) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(USER_FULL_NAME, name);
            contentValues.put(USER_PHONE_NUMBER, phone);
            contentValues.put(USER_PASSWORD, password);

            db.update(USER_TABLE, contentValues, "USER_EMAIL = ?", new String[] {ACTIVE_USER});

            return true;

        }
        // Sends inputed amount from selected outgoing account to selected receiving account
        public boolean sendMoney(Accounts outgoing, Accounts receiving, float amount){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            float outgoingBalance = outgoing.getBalance();
            outgoingBalance -= amount;
            if (outgoingBalance >= 0) {
                int outgoingId = outgoing.getId();
                cv.put(ACCOUNT_BALANCE, outgoingBalance);
                db.update(ACCOUNTS_TABLE, cv, "ACCOUNT_ID = " + outgoingId, null);
                ContentValues cv2 = new ContentValues();
                float reveivingBalance = receiving.getBalance();
                reveivingBalance += amount;
                int receivingId = receiving.getId();
                cv2.put(ACCOUNT_BALANCE, reveivingBalance);
                db.update(ACCOUNTS_TABLE, cv2, "ACCOUNT_ID = " + receivingId, null);
                return true;
            } else {
                return false;
            }
        }

        public String getActiveUser(){
            return ACTIVE_USER;
        }

        public static void setActiveUser(String activeUser) {
            ACTIVE_USER = activeUser;
        }

        public static Accounts getOUTGOING() {
            return OUTGOING;
        }

        public static void setOUTGOING(Accounts account) {
            OUTGOING = account;
        }

        public static Accounts getRECIEVING() {
            return RECIEVING;
        }

        public static void setRECIEVING(Accounts account) {
            RECIEVING = account;
        }

    }