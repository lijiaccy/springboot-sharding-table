package com.lijia.proxy;

    import java.lang.reflect.InvocationHandler;
    import java.lang.reflect.Method;
    import java.lang.reflect.Proxy;


    interface IHello {
        public void test();

        public void test1();
    }


    class Hello implements IHello{
        @Override
        public void test() {
            test1();
            System.out.println("test");
        }

        @Override
        public void test1() {
            System.out.println("test1");
        }
    }

    public class MyInvoke implements InvocationHandler{
        public Object target;

        public MyInvoke(Object target){
            this.target = target;

        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().contains("test")){
                System.out.println("========代理了=======");
            }
            return method.invoke(target,args);
        }

        public static void main(String[] args) {
            MyInvoke myInvoke = new MyInvoke(new Hello());
            IHello iHello = (IHello) Proxy.newProxyInstance(MyInvoke.class.getClassLoader(),new Class[]{IHello.class},myInvoke);
            iHello.test();
        }
    }
