/**
 *  Copyright 2009-2020 PrimeTek.
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.primefaces.diamond.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import javax.enterprise.context.SessionScoped;

@Named
@SessionScoped
public class GuestPreferences implements Serializable {

    private String layout = "static";

    private String componentTheme = "blue";

    private String menuTheme = "bluegray";

    private String logoColor = "white";

    private String scheme = "light";

    private String inputStyle = "outlined";

    private List<MenuTheme> menuThemes;

    private List<ComponentTheme> componentThemes;
    
    @PostConstruct
    public void init() {
        menuThemes = new ArrayList<>();
        menuThemes.add(new MenuTheme("white", "#ffffff", "dark", null));
        menuThemes.add(new MenuTheme("darkgray", "#343a40", "white", null));
        menuThemes.add(new MenuTheme("blue", "#1976d2", "white", "blue"));
        menuThemes.add(new MenuTheme("bluegray", "#455a64", "white", "lightgreen"));
        menuThemes.add(new MenuTheme("brown", "#5d4037", "white", "cyan"));
        menuThemes.add(new MenuTheme("cyan", "#0097a7", "white", "cyan"));
        menuThemes.add(new MenuTheme("green", "#388e3C", "white", "green"));
        menuThemes.add(new MenuTheme("indigo", "#303f9f", "white", "indigo"));
        menuThemes.add(new MenuTheme("deeppurple", "#512da8", "white", "deeppurple"));
        menuThemes.add(new MenuTheme("orange", "#F57c00", "dark", "orange"));
        menuThemes.add(new MenuTheme("pink", "#c2185b", "white", "pink"));
        menuThemes.add(new MenuTheme("purple", "#7b1fa2", "white", "purple"));
        menuThemes.add(new MenuTheme("teal", "#00796b", "white", "teal"));

        componentThemes = new ArrayList<>();
        componentThemes.add(new ComponentTheme("blue", "#42A5F5"));
        componentThemes.add(new ComponentTheme("green", "#66BB6A"));
        componentThemes.add(new ComponentTheme("lightgreen", "#9CCC65"));
        componentThemes.add(new ComponentTheme("purple", "#AB47BC"));
        componentThemes.add(new ComponentTheme("deeppurple", "#7E57C2"));
        componentThemes.add(new ComponentTheme("indigo", "#5C6BC0"));
        componentThemes.add(new ComponentTheme("orange", "#FFA726"));
        componentThemes.add(new ComponentTheme("cyan", "#26C6DA"));
        componentThemes.add(new ComponentTheme("pink", "#EC407A"));
        componentThemes.add(new ComponentTheme("teal", "#26A69A"));
    }

    public String getLayout() {
        return this.layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getComponentTheme() {
        return this.componentTheme;
    }

    public void setComponentTheme(String componentTheme) {
        this.componentTheme = componentTheme;
    }

    public String getMenuTheme() {
        if (this.scheme.equals("light")) {
            return menuTheme; 
        }
        else {
            return this.scheme;
        }
    }

    public void setMenuTheme(String menuTheme) {
        this.menuTheme = menuTheme;
    }

    public String getLayoutClass() {
        return "layout-" + this.layout;
    }

    public String getSidebarThemeClass() {
        if (this.scheme.equals("light")) {
            return "layout-sidebar-" + this.menuTheme; 
        }
        else {
            return "layout-sidebar-" + this.scheme;
        } 
    }

    public String getInputStyleClass() {
        return this.inputStyle.equals("filled") ? "ui-input-filled" : "";
    }

    public String getLogoColor() {
        return logoColor;
    }

    public void setLogoColor(String logoColor) {
        this.logoColor = logoColor;
    }

    public String getInputStyle() {
        return inputStyle;
    }

    public void setInputStyle(String inputStyle) {
        this.inputStyle = inputStyle;
    }

    public List<MenuTheme> getMenuThemes() {
        return this.menuThemes;
    }

    public List<ComponentTheme> getComponentThemes() {
        return this.componentThemes;
    }

    public void changeMenuTheme(MenuTheme menuTheme) {
        this.menuTheme = menuTheme.getName();
        this.logoColor = menuTheme.getLogoColor();

        if (menuTheme.getComponentTheme() != null) {
            this.componentTheme = menuTheme.getComponentTheme();
        }
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public void onLayoutChange() {
        PrimeFaces.current().executeScript("PrimeFaces.DiamondConfigurator.changeLayout('" + layout + "')");
    }

    public void onSchemeChange() {
        if (this.scheme.equals("light")) {
            String _logoColor = menuTheme.equals("white") || menuTheme.equals("orange") ? "dark" : "white";
            PrimeFaces.current().executeScript("PrimeFaces.DiamondConfigurator.changeMenuTheme('" + menuTheme + "', '" + _logoColor +"')");
        }
        else {
            PrimeFaces.current().executeScript("PrimeFaces.DiamondConfigurator.changeMenuTheme('" + scheme + "', 'white')");
        }

        PrimeFaces.current().executeScript("PrimeFaces.DiamondConfigurator.changeScheme('" + scheme + "')");
    }

    public class MenuTheme {

        private String name;

        private String color;
        
        private String logoColor;
        
        private String componentTheme;
    
        public MenuTheme() {}
    
        public MenuTheme(String name, String color, String logoColor, String componentTheme) {
            this.name = name;
            this.color = color;
            this.logoColor = logoColor;
            this.componentTheme = componentTheme;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getColor() {
            return color;
        }
    
        public void setColor(String color) {
            this.color = color;
        }

        public String getLogoColor() {
            return logoColor;
        }

        public void setLogoColor(String logoColor) {
            this.logoColor = logoColor;
        }

        public String getComponentTheme() {
            return componentTheme;
        }

        public void setComponentTheme(String componentTheme) {
            this.componentTheme = componentTheme;
        }
    }

    public class ComponentTheme {

        private String name;

        private String color;
    
        public ComponentTheme() {}
    
        public ComponentTheme(String name, String color) {
            this.name = name;
            this.color = color;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getColor() {
            return color;
        }
    
        public void setColor(String color) {
            this.color = color;
        }
    }
}
