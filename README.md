# Parser generator for XML code-snippets.

## About
This is tool that I developed with 4 my CTU classmates. The goal was to learn to work in team and to create some tool according to software engineering, starting from 
gathering requirements on software, analysis, over implementation, tests and delivery. I tried to be the project leader and I think that it was very beneficial experience.

## Usage
You maybe daily watch exchange rates and sometimes wondered, 
I wish there was a tool that would watch only this rate I'm interested in and store in time interval I set current rate into database.
That's one use-case of our tool.

Firstly you give XML code-snippet or URL, and we create formatted tree view in QUI. Now you select where is the data you are interested in and what data type to expect.
Then specific parser is generated. You can run it on the background, that will check URL you entered and parse out & store found data into database.

See documentation/Uživatelská příručka.pdf

https://www.youtube.com/watch?v=B6qu9mrTFNw&feature=youtu.be

For more information navigate to folder documentation.

## School requirements CZ
Po softwarově inženýrské stránce vytvořte nástroj, který na základě daného útržku XHL kódu (code-snippet) vygeneruje parser 
schopný ukládat definované úseky z code-snippet do objektové struktury, popř. do databáze. 
K tomuto nástroji vytvořte uživatelské rozhraní, ve kterém bude možné snippet přehledně zobrazit stromovou strukturou.

+ Po dodání další semestr rozšířeno o další požadavky: documentation/Seznam funkčních požadavků.docx

# Generátor parserů pro XML code-snippets

Nástroj, který na základě daného útržku XML kódu (code-snippet) vygeneruje parser
schopný ukládat definované úseky z code-snippet do databáze. Uživatel si může specifikovat daný typ databáze.
Aplikace obsahuje uživatelské rozhraní.

### Příprava prostředí

Pro správný a bezproblémový běh programu je nutné mít nainstalovanou Javu ve verzi JDK 1.8. Pro bližší informace následujte prosím do složky [ParserGenerator/documentation](https://gitlab.fit.cvut.cz/ParserGenerator/documentation/blob/master/Instala%C4%8Dn%C3%AD_a_Program%C3%A1torsk%C3%A1_p%C5%99%C3%ADru%C4%8Dka.pdf), kde se nachází v souboru s názvem "Instalační a Programátorská příručka" bližší informace k instalaci.


### Stažení projektu
Pro stažení aktuálního releasu generátoru je potřeba na GitLabu jit do záložky [Tag](https://gitlab.fit.cvut.cz/ParserGenerator/generator/tags),  kde můžeme stáhnout aktuálně vydanou verzi.

### Spuštění
Pro spustění stačí jít přes příkazový řádek do stažené složky a napsat do příkazového řádku

```
java -jar "Parser.jar" 
```

Tímto spustíme GUI verzi programu.

###  Ovládání programu
Po spuštění programu vybereme soubor. Je zde také možnost si soubor stáhnout přímo z webu. 
Po zadání souboru zadáme XPath řetězec. 

```
//   když chceme vybrat všechny elementy
//TD najdeme v souboru všechny výskyty tagu TD a vypíšeme všechny jeho potomky
```

Poté vybereme jednotlivé části kódů, vyplníme název tabulky, sloupce a typ dat. Nakonec zaškrtneme tlačítko "Přidat data do výběru".

Až vybereme všechny zajímavé části, tak necháme vygenerovat generátor parseru. Je zde možnost ho rovnou i zkompilovat.


### Uložení výsledků do databáze
V míste, kam jsme vygenerovali generátor přes příkazový řádek můžeme uložit data do databáze. 

Příkaz se skládá z:
- java -jar
- vygenerovany generátor
- xml soubor, který jsme dávali do generátoru
- typ databáze
- connection string

```
$ java -jar xhtmlParser.jar  test.xhtml Oracle 'sp_16_valenta/<HESLO>@oracle.fit.cvut.cz:1521/ORACLE'
```
Analogicky pro jine dtb, viz [Programatorska prirucka](https://gitlab.fit.cvut.cz/ParserGenerator/documentation/blob/master/Instala%C4%8Dn%C3%AD_a_Program%C3%A1torsk%C3%A1_p%C5%99%C3%ADru%C4%8Dka.pdf)

Nakonec program vypíše informaci o úspěšném uložení, lze zkontrolovat, že bylo uloženo do dtb.
```
Lis 30, 2016 6:16:29 ODP. parser.model.dao.AbstractDao prepareSqlScript
INFO: INSERT INTO FEMALE (HEIGHT) VALUES ('163')
Lis 30, 2016 6:16:29 ODP. parser.model.dao.AbstractDao prepareSqlScript
INFO: INSERT INTO MALE (EYE) VALUES ('2')
```
