
class Incremenator extends Thread
{
    private volatile boolean mIsIncrement = true;
//    private int mValue = 0;
    public void changeAction()	//Меняет действие на противоположное
    {
        mIsIncrement = !mIsIncrement;
    }
    @Override
    public void run()
    {
        do
        {
            if(!Thread.interrupted())	//Проверка прерывания
            {
                if(mIsIncrement) InterruptSample.mValue++;	//Инкремент
                else InterruptSample.mValue--;			//Декремент

                //Вывод текущего значения переменной
                System.out.print(InterruptSample.mValue + " ");
            }
            else
                return;		//Завершение потока

            try{
                Thread.sleep(1000);		//Приостановка потока на 1 сек.
            }catch(InterruptedException e){
                return;	//Завершение потока после прерывания
            }
        }
        while(true);
    }
}

public class InterruptSample {
    //Переменая, которой оперирует инкременатор
    public volatile static int mValue = 0;
    static Incremenator mInc;	//Объект побочного потока
    public static void main(String[] args)
    {
        mInc = new Incremenator();	//Создание потока
        System.out.print("Значение = ");
        mInc.start();	//Запуск потока
        //Троекратное изменение действия инкременатора
        //с интервалом в i*2 секунд
        for(int i = 1; i <= 3; i++)
        {
            try{
                Thread.sleep(i*2*1000);		//Ожидание в течении i*2 сек.
            }catch(InterruptedException e){}
            System.out.print(" Смена ");
            mInc.changeAction();	//Переключение действия
        }
        mInc.interrupt();	//Прерывание побочного потока
    }
}
