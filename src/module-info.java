module LnTFinalProject_C_RicardoCuthbert {
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.base;
	requires java.sql;
	
	opens main;
	opens main.model;
	opens main.view;
	opens database;
}