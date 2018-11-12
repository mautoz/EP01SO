# EP01SO
EP1 de SO - Escalonador de processos com Round Robin

Primeiro Exerc´ıcio-Programa

1 Escalonador de Processos
Para este trabalho, voc^es devem se organizar em grupos de at´e 4 (quatro) pessoas. Cada grupo
deve ent~ao implementar um escalonador de tarefas para Time Sharing em uma m´aquina com
um ´unico processador, criando assim um sistema simples de multiprograma¸c~ao. A linguagem
usada na constru¸c~ao do escalonador deve ser Java.
A m´aquina foi criada para rodar pequenos programas, em que cada processo pode contar,
no m´aximo, com 2 registradores de uso geral (al´em do Contador de Programa, como registrador
de uso espec´ıfico). Esses registradores s~ao conhecidos internamente como X e Y. Al´em disso, o
processador para o qual voc^es ir~ao construir o escalonador ´e extremamente simples, possuindo
apenas 4 instru¸c~oes:
1. Atribui¸c~ao: na forma X=<valor> ou Y=<valor>, onde <valor> ´e um n´umero inteiro e
X e Y s~ao os registradores de uso geral usados pelo processo (note a aus^encia de espa¸co
antes e depois do ‘=’).
2. Entrada e sa´ıda: representada pela instru¸c~ao E/S (que faz as vezes de uma chamada ao
sistema)
3. Comando: a tarefa executada pela m´aquina, representada pela instru¸c~ao COM
4. Fim de programa: chamada com a ´unica finalidade de remover o programa da mem´oria,
executando a limpeza final. Representada pela instru¸c~ao SAIDA
Sabe-se que um processo pode estar em um dos seguintes estados: Executando, Pronto
ou Bloqueado. Enquanto h´a apenas um processo executando, pode haver v´arios prontos
para executar ou bloqueados, esperando alguma requisi¸c~ao de E/S se completar. Assim, sua
implementa¸c~ao deve contemplar uma lista de processos prontos e outra de bloqueados.
Na aus^encia de um clock que comande a preemp¸c~ao, quem efetivamente rodar´a as instru¸c~oes dos processos ´e o escalonador, que l^e a instru¸c~ao e a executa, funcionando como
um interpretador. Isso deixa o processo mais lento, naturalmente, mas garante o compartilhamento de tempo. Dentro do escalonador, a fila de processos prontos deve ser ordenada
conforme a prioridade do processo, enquanto que a fila de bloqueados ´e ordenada por ordem
de chegada.
1Seu sistema deve ent~ao possuir uma Tabela de Processos, representando todos os programas
que est~ao rodando simultaneamente. Cada linha da tabela deve conter uma refer^encia ao Bloco
de Controle de Processo (BCP), sendo que este cont´em toda a informa¸c~ao necess´aria para que
o processo, ap´os interrompido temporariamente, possa voltar a rodar. Ou seja, o BCP deve
conter, pelo menos, o Contador de Programa, o estado do processo (executando, pronto ou
bloqueado), sua prioridade, o estado atual de seus registradores de uso geral, uma refer^encia `a
regi~ao da mem´oria em que est´a o c´odigo do programa executado (representado, por exemplo,
por um arranjo de Strings, que j´a ´e uma refer^encia natural `a mem´oria em Java) e o nome do
programa.
Vale notar que h´a somente o segmento de texto na mem´oria (representado, por exemplo,
por um arranjo de 21 posi¸c~oes), em que ´e armazenado o c´odigo do programa. Por n~ao conter
nem vari´aveis nem desvios (sub-rotinas etc), n~ao h´a sentido em ter um segmento de dados e
da pilha. Al´em disso, lembre que, em java, qualquer inst^ancia a um objeto ou arranjo j´a ´e
uma refer^encia a mem´oria externa ao objeto em que essa inst^ancia est´a declarada.
Os programas executados ser~ao dados na forma de arquivos-texto (ver 1.1). O escalonador
deve, ent~ao, carregar cada bloco de comandos (correspondente a um arquivo) na mem´oria,
posicionando seu BCP na Tabela de Processos e na fila de processos prontos, seguindo sempre
sua ordem de prioridade. A prioridade de cada processo ´e, por sua vez, carregada a partir
de um arquivo intitulado \prioridades.txt", que apresenta, a cada linha, a prioridade de cada
processo (quando estes s~ao ordenados em ordem alfab´etica pelo nome de seus arquivos). Nesse
caso, quanto maior o valor do n´umero contido no arquivo, maior a prioridade do processo.
Como uma simplifica¸c~ao adicional, em vez de fatias de tempo, o escalonador ir´a permitir
que cada processo no estado executando rode no m´aximo n com comandos (ou seja, o quantum
ser´a de n com comandos, em vez de uma quantia de milissegundos). Esse n´umero de comandos
´e uma simula¸c~ao do tempo de ocupa¸c~ao do processador relacionado ao time-sharing, e deve ser
lido de um arquivo denominado \quantum.txt". Esse arquivo conter´a t~ao somente um inteiro.
Uma vez tendo carregado todos os processos, o escalonador come¸ca a rod´a-los, usando o
seguinte algoritmo de prioridades (bastante semelhante ao usado no Linux):
1. Inicialmente, distribua um n´umero de cr´editos, a cada processo, igual `a sua prioridade;
2. Crie m´ultpilas filas, de acordo com n´umero de cr´editos (do maior para o menor)
3. Em cada fila, aplique o algoritmo round robin
4. Cada processo deve executar um n´umero fixo de instru¸c~oes (seu quantum):
(a) Inicialmente recebe 1 quantum, e ´e suspenso
(b) Ent~ao, o processo perde 1 cr´edito
(c) O processo ´e reposicionado na fila de processos prontos e recebe 2 quantas para ser
utilizado quando for escalonado
(d) Ao ser suspenso novamente perde 1 cr´edito e recebe 4, 8, 16 quantas.
2(e) O primeiro da fila ´e posto a rodar (note que, dependendo da prioridade, pode ser
o mesmo processo de antes)
(f) Quanto todos os processos estiverem com zero cr´edito, ent~ao os cr´editos s~ao redistribu´ıdos, conforme sua prioridade, voltando assim ao passo 1;
5. Se, durante a execu¸c~ao de um quantum, o processo fizer uma entrada ou sa´ıda (instru¸c~ao
\E/S"):
(a) Ele ser´a marcado como bloqueado, sendo ent~ao transferido para a lista de bloqueados, perde 1 cr´edito e recebe o dobro de quantum;
(b) A ele ´e atribu´ıdo um tempo de espera (inteiro representando quantos quanta ele
deve esperar para rodar novamente);
(c) A cada processo que passe pelo estado executando (ou seja, ao sair desse estado),
esse tempo de espera ´e decrementado (note que todos na fila de bloqueados t^em
seu tempo decrementado);
(d) Cada processo fica bloqueado at´e que dois outros processos passem pelo estado
executando (esse ´e o tempo de espera), n~ao importando quantos comandos cada
um executou (ou seja, se usou todo seu quantum ou n~ao). Essa ´e uma simula¸c~ao
do tempo de espera por um dispositivo de E/S (note que, uma vez que o tempo de
resposta de uma E/S ´e igual para todos, a lista de processos bloqueados acaba se
comportando como uma fila comum);
(e) Quando o tempo de espera de algum processo bloqueado chegar a zero, este deve
receber o status de pronto, sendo ent~ao removido da fila de bloqueados e inserido
na fila de processos prontos, na posi¸c~ao correspondente ao seu n´umero atual de
cr´editos. Note que ele n~ao necessariamente ocasionar´a a preemp¸c~ao do processo em
execu¸c~ao, ou seja, o escalonador escolher´a sempre o de maior n´umero de cr´editos;
(f) Quando esse processo for rodar novamente, deve reiniciar a partir da instru¸c~ao
seguinte `a E/S (uma vez que o PC ´e armazenado no BCP e este cont´em a instru¸c~ao
seguinte `a E/S). Atente que a instru¸c~ao de E/S foi contada nas estat´ısticas do
sistema durante o momento anterior ao bloqueio (ver Se¸c~ao 1.2).
6. Se n~ao houver nenhum processo em condi¸c~ao de ser executado (ex: existirem apenas
dois processos e ambos estiverem bloqueados), deve-se decrementar os tempos de espera
de todos os processos na fila de bloqueados, at´e que um chegue a zero, podendo ent~ao
ser rodado (como visto no item 4e).
7. Ao encontrar o comando SAIDA, o escalonador deve remover o processo em execu¸c~ao
da fila apropriada e da tabela de processos.
Vale lembrar que apenas um m´aximo de n com instru¸c~oes (de qualquer um dos 4 tipos
definidos mais adiante) podem ser executadas por vez pelo processador quando o processo
estiver no estado executando. Quando isso ocorrer, o processo terminou seu quantum e deve
3ir para sua posi¸c~ao na fila de prontos (n~ao necessariamente ao seu final). Um novo processo
dessa fila deve ent~ao ir para o estado executando. Note que isso implica saber qual ser´a o
pr´oximo comando a ser executado nesse processo, ou seja, saber o conte´udo de seu Contador
de Programa.
1.1 Entrada
Ser~ao dados como entrada 10 arquivos-texto, fornecidos dentro do diret´orio \processos" (no
anexo \EP1.zip"), em que cada arquivo dentro de \processos" corresponde a um processo,
constru´ıdo da seguinte forma:
1. O nome do arquivo corresponde a um inteiro sequencial de dois d´ıgitos (01.txt, 02.txt
etc)
2. A primeira linha do arquivo cont´em o nome do programa
3. As linhas seguintes apresentam uma sequ^encia qualquer de instru¸c~oes aceitas pela m´aquina,
terminando com SAIDA
4. Cada processo ser´a composto por no m´aximo 21 comandos (incluindo SAIDA). Assim,
cada arquivo conter´a, no m´aximo, 22 linhas (uma linha por comando, al´em do nome),
do tipo:
• <registrador>=<valor>
• COM
• E/S
• SAIDA
Um exemplo de arquivo seria:
TESTE-1
X=8
COM
COM
COM
E/S
Y=10
X=2
COM
E/S
SAIDA
Dentro do mesmo diret´orio, ser~ao tamb´em fornecidos o arquivo \prioridades.txt", que
define as prioridades de cada processo (processo 01.txt na linha 1, processo 02.txt na linha 2 e
4assim por diante), al´em do arquivo \quantum.txt", que cont´em um ´unico inteiro, representando
o tamanho do quantum a ser usado (ou seja, o n´umero de instru¸c~oes rodadas por surto de
CPU).
1.2 Sa´ıda
Durante o processamento, o escalonador deve construir um logfile, denominado \logXX.txt",
em que XX ´e o valor do quantum escolhido (2 d´ıgitos) . Nesse logfile, o escalonador deve
gravar:
1. Os nomes dos processos carregados, na ordem em que est~ao na fila de prontos
2. O nome do processo que est´a sendo interrompido, juntamente com o n´umero de instru¸c~oes executadas at´e seu interrompimento (ex: \Interrompendo TESTE-1 ap´os 3 instru¸c~oes"). Essas instru¸c~oes referem-se `as executadas no ´ultimo quantum, n~ao o n´umero
total desde o in´ıcio do processo.
3. O nome do processo que passar´a a ser executado (ex: \Executando TESTE-1")
4. O nome do processo que inicia uma E/S (ex: \E/S iniciada em TESTE-1")
5. O nome do processo que terminou (ou seja, teve todos seus comandos executados),
juntamente com o valor final dos registradores X e Y (ex: \TESTE-1 terminado. X=2.
Y=3"). Se o programa n~ao usar um dos registradores, ele ser´a zero.
Ao final do sistema, voc^e deve incluir no logfile o n´umero m´edio, por processo, de trocas de
processo (ou seja, a m´edia, dentre todos os processos, do n´umero de vezes em que cada processo
deixa o processador, incluindo-se o momento de seu t´ermino), o n´umero m´edio de instru¸c~oes
executadas por quantum (corresponde `a m´edia, dentre todos os processos, do n´umero de
instru¸c~oes executadas at´e o processo ser interrompido, seja por E/S, seja porque executou
n com instru¸c~oes, seja porque terminou no meio de seu quantum { ´e uma m´edia das instru¸c~oes
executadas em cada quantum, para todos os processos), al´em do quantum usado.
Um exemplo de logfile ´e (os valores s~ao meramente ilustrativos, n~ao correspondendo a
nenhum exemplo real. Notas entre par^enteses s~ao coment´arios para voc^es. N~ao devem constar
do log.):
Carregando TESTE-1
Carregando TESTE-3
Carregando TESTE-2
Executando TESTE-1
Interrompendo TESTE-1 ap´os 3 instru¸c~oes
Executando TESTE-3
E/S iniciada em TESTE-3
Interrompendo TESTE-3 ap´os 2 instru¸c~oes (havia um comando antes da E/S)
Executando TESTE-2
5E/S iniciada em TESTE-2
Interrompendo TESTE-2 ap´os 1 instru¸c~ao (havia apenas a E/S)
TESTE-2 terminado. X=0. Y=3
Executando TESTE-1
...
TESTE-1 terminado. X=3. Y=1
...
TESTE-3 terminado. X=4. Y=0
MEDIA DE TROCAS: 5
MEDIA DE INSTRUCOES: 2.5
QUANTUM: 3
1.3 Implementa¸c~ao
O logfile deve ser atualizado toda vez que o escalonador tiver que tomar uma decis~ao de
escalonamento, conforme descrito em 1.2, ou seja:
• Ao se iniciar (carregar) um processo
• Ao se executar um processo (seja pela primeira vez ou volta de fila de prontos)
• Ao se terminar um processo
• Ao se interromper um processo (bloqueio de E/S, fim do quantum ou t´ermino natural
do processo)
Tamb´em devem ser inclu´ıdas as vezes em que a instru¸c~ao a ser executada for uma E/S,
bem como as estat´ısticas gerais do sistema (m´edias etc.).
Seu escalonador deve se chamar Escalonador.java. Ao ser chamado da linha de comando,
o sistema ir´a carregar os programas fornecidos (os 12 arquivos estar~ao em um subdiret´orio
chamado \processos", dentro do diret´orio do seu programa, descompactados), coloc´a-los na
fila de prontos, orden´a-los conforme suas prioridades (arquivo \prioridades.txt"), e rod´a-los
usando o valor contido em \quantum.txt" como tamanho do quantum.
Em seu c´odigo, devem-se deixar expl´ıcitas as seguintes estruturas (com coment´arios e modulariza¸c~ao adequada):
• BCP, contendo:
{ Contador de Programa
{ Estado do processo
{ Prioridade
{ Registradores de uso geral
{ Refer^encia ao segmento de texto do programa
6{ Possivelmente os cr´editos, embora estes, e somente estes, possam ser deixados em
estrutura `a parte
• Tabela de processos;
• Lista de processos prontos;
• Lista de processos bloqueados.
1.4 Situa¸c~oes Adversas
Algumas situa¸c~oes que podem ocorrer:
• E se, ap´os descontado o cr´edito, um processo que acabou de rodar (primeiro da fila)
tiver prioridade igual ao segundo da fila?
R: O que ´e mais custoso? Fazer uma troca completa de contexto, ou manter aquele que
estava rodando antes? Embora nesse EP o custo seja id^entico, quero que analisem o que
seria melhor, caso fosse real.
• E se, durante a execu¸c~ao, todos da fila de prontos ficarem com zero cr´edito, ainda existindo processos com cr´edito na fila de bloqueados (ou seja, impedindo a redistribui¸c~ao)?
R: Nesse caso, a fila de prontos torna-se uma fila comum, at´e que os processos bloqueados
voltem da fila de bloqueados (caso em que acabar~ao tendo prioridade naturalmente, por
possu´ırem mais cr´editos). A redistribui¸c~ao s´o ´e feita quando todos estiverem com zero
cr´edito.
• SAIDA conta como um comando executado?
R: Sim. Ela deve entrar nas estat´ısticas.
• Quando conto E/S nas estat´ısticas, quando come¸ca ou quando se completa?
R: No caso de E/S, a instru¸c~ao entra para as estat´ısticas (n´umero de instru¸c~oes por
quantum) quando iniciar, ou seja, imediatamente antes de bloquear.
• E se dois processo tiverem prioridades iguais quando inicia-se o programa?
R: Eles devem ficar em ordem alfab´etica pelo nome do arquivo que os cont´em.
• E se, ao ter seu cr´edito reduzido e tiver que ser reposicionado na fila de prontos, houver
outros processos nessa fila com a mesma prioridade do que foi reduzido?
R: Coloque-o `a frente destes (ele era mais importante antes).
1.5 Testes
Como forma de teste e avalia¸c~ao do sistema, voc^es devem gerar o arquivo de log para diferentes
valores de n com (pelo menos 10 valores diferentes, distribuidos de maneira uniforme em um
intervalo que voc^e julgue ´util { lembre que h´a um n´umero m´aximo de instru¸c~oes em um
programa), informando, para cada quantum definido:
7• N´umero m´edio de trocas de processo, por processo.
• N´umero m´edio de instru¸c~oes executadas por grupo de n com (quantum).
Com base nesses dados, construam um relat´orio dizendo qual o valor de n com que voc^es
consideram mais adequado, levando em conta o n´umero de trocas do processo, bem como a
rela¸c~ao entre o tamanho de n com e a m´edia de instru¸c~oes executadas por quantum. Aten¸c~ao!
N~ao tirem coelhos de cartolas! Fundamentem suas conclus~oes com base no comportamento do
sistema; apresentem gr´aficos e tabelas para convencer o leitor do relat´orio de que sua escolha
est´a coerente.
O relat´orio deve ser entregue em pdf.
1.6 Material para Entrega
A entrega ser´a feita unica e exclusivamente via Tidia. Voc^e deve criar um arquivo \No USP.zip"
(em que No USP ´e seu n´umero USP) contendo o seguinte material:
• Logfiles gerados, organizados conforme o quantum definido pelo usu´ario (para cada quantum haver´a um logfile diferente)
• C´odigo java do programa
• Relat´orio de avalia¸c~ao do sistema, em pdf, conforme descrito acima
Deve ser submetido um ´unico arquivo por grupo (ou seja, um ´unico integrante ir´a fazer a
submiss~ao), sendo que o relat´orio dever´a conter o nome e n´umero usp de todos os participantes.
1.7 Data de Entrega
O prazo de entrega ´e 01 de Outubro de 2017.
1.8 Observa¸c~oes Te´oricas
• Note que em nenhum momento fala-se do contexto do escalonador. Isso porque, nesse
trabalho, os registradores e demais recursos usados pelo escalonador est~ao transparentes
ao sistema. Naturalmente, isso n~ao corresponde `a realidade em um processador, estando
mais alinhado ao que ocorre em uma m´aquina virtual, em que o escalonador da m´aquina
n~ao se preocupa com seu pr´oprio contexto, tratando t~ao somente de gerenciar o contexto
dos processos que nele rodam.
8
