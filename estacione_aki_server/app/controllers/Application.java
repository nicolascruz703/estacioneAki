package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;
import play.libs.*;
import play.cache.*;
 
import java.util.*;
import models.*;
 
public class Application extends Controller {
    
    @Before
    static void addDefaults() {
    }
 
    public static void index() {
        render();
    }
    
    public static void show() {
        render();
    }
  
}
