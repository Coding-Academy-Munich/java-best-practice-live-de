// -*- coding: utf-8 -*-
// %% [markdown]
// <!--
// clang-format off
// -->
//
// <div style="text-align:center; font-size:200%;">
//  <b>SOLID: Liskov-Substitutions-Prinzip</b>
// </div>
// <br/>
// <div style="text-align:center; font-size:120%;">Dr. Matthias Hölzl</div>
// <br/>
// <div style="text-align:center;">Coding-Akademie München</div>
// <br/>

// %% [markdown]
//
// # SOLID: Liskov Substitutions-Prinzip
//
// Ein Objekt einer Unterklasse soll immer für ein Objekt der Oberklasse
// eingesetzt werden können.

// %% [markdown]
//
// ## LSP Verletzung

// %%
public class Rectangle {
    protected float length;
    protected float width;

    public Rectangle(float l, float w) {
        this.length = l;
        this.width = w;
    }

    public float area() {
        return length * width;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float l) {
        this.length = l;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float w) {
        this.width = w;
    }
}

// %%
public class Square extends Rectangle {
    public Square(float l) {
        super(l, l);
    }

    @Override
    public void setLength(float l) {
        this.length = l;
        this.width = l;
    }

    @Override
    public void setWidth(float w) {
        this.length = w;
        this.width = w;
    }
}
// %%
public static void resizeRectangle(Rectangle r) {
    r.setLength(4);
    r.setWidth(5);
    System.out.println("Length: " + r.getLength() + ", Width: " + r.getWidth() + ", Area: " + r.area());
}

// %%
Rectangle r = new Rectangle(3, 4);
resizeRectangle(r);

// %%
Square s = new Square(3);
resizeRectangle(s);
