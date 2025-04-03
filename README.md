 Descrição do Projeto:
Este projeto realiza web scraping no portal da Agência Nacional de Saúde Suplementar (ANS) para baixar os Anexos I e II (em formato PDF) referentes à atualização do Rol de Procedimentos, compactando-os em um arquivo ZIP.

-Funcionalidades
Acesso automatizado ao site da ANS

Identificação e download dos Anexos I e II em PDF

Criação de diretório para armazenamento local

Compactação dos arquivos em formato ZIP

-Pré-requisitos
Java JDK 8 ou superior

Maven (para gerenciamento de dependências)

Conexão com internet para acesso ao site da ANS

-Dependências
JSoup (para web scraping)

Bibliotecas padrão do Java para manipulação de arquivos e ZIP

---- Como Executar
Clone este repositório:
bash
Copy
git clone [URL_DO_REPOSITÓRIO]

Compile o projeto:
bash
Copy
mvn clean package

Execute o programa:
bash
Copy
java -jar target/webscraper-ans.jar

 Estrutura de Arquivos
downloads/: Diretório criado automaticamente para armazenar os PDFs baixados

anexos_ans.zip: Arquivo ZIP gerado contendo todos os anexos

Você pode modificar as seguintes constantes na classe principal:

java
Copy
private static final String URL_ANS = "https://www.gov.br/ans/..."; // URL do portal
private static final String DOWNLOAD_DIR = "downloads"; // Pasta de downloads
private static final String ZIP_FILE = "anexos_ans.zip"; // Nome do arquivo ZIP
--- Limitações
Depende da estrutura atual do site da ANS (mudanças podem quebrar o scraper)

Baixa apenas os dois primeiros arquivos identificados como Anexo I e II

--- Licença
Este projeto está licenciado sob a licença MIT - veja o arquivo LICENSE para detalhes.

--- Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.

--- Contato
Para dúvidas ou sugestões, entre em contato com [janilton.fsf@gmail.com]

