package com.pairliu.learning.concurrency.immutability;

public class ImmutableObject {
    private final String field1;
    private final int field2;
    
    private ImmutableObject(Builder builder) {
        field1 = builder.field1;
        field2 = builder.field2;
    }
    
    public String getField1() {
        return field1;
    }

    public int getField2() {
        return field2;
    }
    
    public static class Builder implements ObjBuilder<ImmutableObject> {
        
        private String field1;
        private int field2;

        //Getters are unnecessary because out class can access inner class's private member
        public Builder setField1(String field1) {
            this.field1 = field1;
            return this;
        }

        public Builder setField2(int field2) {
            this.field2 = field2;
            return this;
        }

        public ImmutableObject build() {
            return new ImmutableObject(this);
        }
        
    }
    
    public static void main(String[] args) {
        Builder builder = new ImmutableObject.Builder();
        ImmutableObject o = builder.setField1("A good day").setField2(15).build();
        System.out.println(o.getField1());
        System.out.println(o.getField2());
    }

}
