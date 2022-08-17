# App Calculadora de Trilateração
version code : 1.1.0

version name: 1

# Introdução
Aplicativo que aponta num plano cartesiano as coordenadas de um item lido por 3 leitores. 

É necessário apontar dentro do plano cartesiano as coordenadas dos 3 leitores (x e y) e as distâncias que os leitores apontaram do objeto lido.

O cálculo feito é o representado pela imagem abaixo: 


![image](https://user-images.githubusercontent.com/43394727/185010456-1698356b-087c-493c-8c44-e7feb615d42f.png)

**IMPORTANTE**

Para o cálculo funcionar é preciso que o posicionamento dos leitores indicado dentro do app seja triangulado. Caso contrário o cálculo não funcionará. Também é necessário que o ponto lido esteja dentro da área do triângulo. E que as leituras gerem 6 itersecções.

Exemplo:

![geogebra-export (1)](https://user-images.githubusercontent.com/43394727/185232835-719715bb-0b7c-4309-ad59-ab55437508d5.png)

Coordenada dos Leitores:

A (1,1)
B (3,4)
C (5,1)

Distância lida pelo leitor A
A -> E = 1.86

Distância lida pelo leitor B
B -> G = 2.49

Distância lida pelo leitor C
C -> F = 3.5

O resultado desta trilateração é (2.14, 1.84).

O triângulo formado no desenho acima é o que indica aproxidamente a localização do item lido, estima-se que esteja próximo do baricentro deste triângulo. 

Os leitores sempre apontam uma distância aproximada, nunca exata, por isso as leituras apontam para pontos diferentes.

# Imagem App
![Screenshot_20220816_215751](https://user-images.githubusercontent.com/43394727/185010620-ca96a6ed-a556-40d0-9c40-11d9ce45dd0b.png)
