## 装饰者模式（Decorator Pattern）

Decorator Pattern叫装饰模式，或装饰者模式，以前叫包装器模式（Wrapper，GoF在92-93年由Wrapper改为Decorator）。
装饰模式是在不必改变原类文件和使用继承的情况下，动态地扩展一个对象的功能。它是通过创建一个包装对象，也就是装饰来包裹真实的对象。

Decorator模式的工作原理是：可以创建始于Decorator对象（负责新的功能的对象）终于原对象的一个对象“链”。
![示意图](https://img-blog.csdn.net/20160810170534589?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

装饰者模式隐含的是通过一条条装饰链去实现具体对象，每一条装饰链都始于一个Componet对象，每个装饰者对象后面紧跟着另一个装饰者对象，而对象链终于ConcreteComponet对象。 

### 意图

动态地给一个对象添加一些额外的职责。就增加功能来说，Decorator模式相比生成子类更为灵活。

### 适用性

1. 需要扩展一个类的功能，或给一个类添加附加职责。
2. 需要动态的给一个对象添加功能，这些功能可以再动态的撤销。
3. 需要增加由一些基本功能的排列组合而产生的非常大量的功能，从而使继承关系变的不现实。
4. 当不能采用生成子类的方法进行扩充时。一种情况是，可能有大量独立的扩展，为支持每一种组合将产生大量的子类，使得子类数目呈爆炸性增长。另一种情况可能是因为类定义被隐藏，或类定义不能用于生成子类。


### 结构：
![结构](https://img-blog.csdn.net/20160810171004736?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
### 在装饰模式中的各个角色有：
1. 抽象构件（Component）角色：给出一个抽象接口，以规范准备接收附加责任的对象。
2. 具体构件（Concrete Component）角色：定义一个将要接收附加责任的类。
3. 装饰（Decorator）角色：持有一个构件（Component）对象的实例，并实现一个与抽象构件接口一致的接口。
4. 具体装饰（Concrete Decorator）角色：负责给构件对象添加上附加的责任。