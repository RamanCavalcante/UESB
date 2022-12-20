/*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
Autor....: Raman Melo Cavalcante
Matrícula: 201820754
Inicio...: 09 de agosto de 2012
Alteracao: 14 de agosto de 2012
Nome.....: Principal.cpp
Funcao...: realizar a simulacao da arvore genealogica
=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
#include <iostream>
#include <unistd.h>
using namespace std;

static pid_t pai, primeiroFilho, segundoFilho, primeiroNeto, segundoNeto, terceiroFilho,primeiroBisNeto;

int ano = 0;

int main(void){
  pai = getpid();
  while(true){
    if(getpid() == pai){
      cout<<ano<<endl;
    }
      
    switch(ano){
      case 0:
      if(getpid() == pai){
        cout<<"Nasce o pai, id Pai"<<pai<<endl;
        
      }
      break;
      case 22:
        if (getpid() == pai){
          cout << "O Pai tem o primeiro filho aos "<<ano << " anos, id pai: "<<pai<< endl;
          primeiroFilho = fork();
          if (primeiroFilho == 0){
            primeiroFilho = getpid();
            cout<<"id Filho: "<<primeiroFilho<<endl;
          }
        }
        break;
      case 25:
        if(getpid() == pai){
          cout<<"O Pai tem o segundo filho aos "<<ano<<"anos, id Pai: "<<pai<<endl;
          segundoFilho = fork();
          if(segundoFilho == 0){
            segundoFilho = getpid();
            cout<<"id Filho: "<<segundoFilho<<endl;

          } 
        }
        break;
      case 38:
        if (getpid() == primeiroFilho) {
        cout << "Pai eh avo (primeiro filho) aos " << ano << " anos, id Pai: "<<primeiroFilho << endl;
        primeiroNeto = fork();
        if(primeiroNeto == 0){
          primeiroNeto = getpid();
          cout<<"id Filho: "<<primeiroNeto<<endl;
        }      
      }
        break;
      case 45:
        if(getpid()==segundoFilho){
          cout<<"O pai e avo(segundo filho) aos"<<ano<<" anos, id Pai: "<<segundoFilho<<endl;
          segundoNeto = fork();
          if(segundoNeto == 0){
            segundoNeto = getpid();
            cout<<"id Filho: "<<segundoNeto<<endl;
          }
        }
        break;
      case 32:
        if(getpid()==pai){
          cout<<"O pai tem o terceiro filho: aos  "<<ano<<" anos, id Pai: "<<pai<<endl;
          terceiroFilho = fork();
          if(terceiroFilho == 0){
            terceiroFilho = getpid();
            cout<<"id Filho: "<<terceiroFilho<<endl;
          }
        }
        break;
      case 68:
      if(getpid()==primeiroNeto){
        cout<<"O pai é bisavo (primeiro filho) aos "<<ano<<" anos, id Pai:"<<pai<<endl;
        primeiroBisNeto = fork();
        if(primeiroBisNeto ==0){
          primeiroBisNeto = getpid();
          cout<<"id Filho: "<<primeiroBisNeto<<endl;
        }
      }
      break;
     
      case 83:
      if (getpid() == primeiroFilho){
        cout << "O Primeiro filho morre aos 61 anos, id: "<<primeiroFilho<< endl;
        _exit(0);
      }
      break;
      case 80:
        if(getpid()==segundoFilho){
          cout<<"O seugndo filho morre aos 55 anos, id: "<<segundoFilho<<endl;
          _exit(0);
        }
        if(getpid()==primeiroBisNeto){
          cout<<"O bisneto morre aos 12 anos, id: "<<primeiroBisNeto<<endl;
          _exit(0);
        }
      break;
      case 87:
        if(getpid()==terceiroFilho){
          cout<<"O terceiro filho morre aos 55 anos, id: "<<terceiroFilho<<endl;
          _exit(0);
        }
      case 73:
        if(getpid()==primeiroNeto){
          cout<<"O primeiro neto morre aos 35 anos, id: "<<primeiroNeto<<endl;
          _exit(0);
        }
        break;
      case 78:
        if(getpid()==segundoNeto){
          cout<<"O segundo neto morre aos 33 anos, id: "<<segundoNeto<<endl;
          _exit(0);
        }
        break;
      case 90:
        if(getpid()==pai){
          cout<<"O pai morre aos "<<ano<<" anos, id Pai: "<<pai<<endl;
          _exit(0);
        }
     }
  sleep(1);
  ano++;
  }
  
}
