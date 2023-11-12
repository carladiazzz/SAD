import java.util.*;
import java.io.*;

class Value extends Observable {
  int value, max;
  
  Value() {
    value = 0;
  }
  
  void inc(ConsolePercent conPer) throws IOException {
    setChanged();
    if (value == max){
      notifyObservers(new ConsoleBar.Command(ConsoleBar.Opcode.BELL)); // push style
      conPer.update(null,new ConsolePercent.Command(ConsolePercent.Opcode.BELL));
    }else {
      value++;
      notifyObservers(new ConsoleBar.Command(ConsoleBar.Opcode.INC));
      conPer.update(null,new ConsolePercent.Command(ConsolePercent.Opcode.INC));
    }
  }
  
  void dec(ConsolePercent conPer) {
    setChanged();
    if (value == 0){
      notifyObservers(new ConsoleBar.Command(ConsoleBar.Opcode.BELL));
      conPer.update(null,new ConsolePercent.Command(ConsolePercent.Opcode.BELL));
    }else {
      value--;
      notifyObservers(new ConsoleBar.Command(ConsoleBar.Opcode.DEC));
      conPer.update(null,new ConsolePercent.Command(ConsolePercent.Opcode.INC));
    }
  }
  
  int get() {
  	return value;
  }
  
  void setMax(int max) {
    this.max = max;
  }
}

