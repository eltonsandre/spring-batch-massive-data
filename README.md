# spring-batch-massive-data
Processamento de dados massivos por arquivo usando spring batch.


**Como funciona:**
o projeto ficara escutando o diretório **$HOME/data/input/{file_name}.dat** a cada 20 segundo para verificar se foi adicionado arquivos do tipo .dat.
A adição do arquivo dispara o Job e fara o procesamento batch que resultará na saida do arquivo processado em **$HOME/data/output/{file_name}.done
.dat**.

#
**Como rodar:**
- via docker-compose:
```shell script
docker-compose up

# ou separado caso tenha alterado algo no projeto

docker-compose up mysql
docker-compose up --build massive-batch
```

**Testando e gerando resultado**
- execute o projeto
- adicione o arquivo **./data/input/massive.dat** no diretorio **${user.home}/data/input/** 
- o resultado deve ser gerado em **${user.home}/data/output/massive.done.dat**

#
A localização do diretorio de entrada e saida, tipo de arquivo e o tempo de verificação do diretório pode ser alterada pelo arquivo(application.yml
) de propriedade ou passado por paramentro.

###### _application.yml_
```yaml
massive.data:
  input: ${user.home}/data/input 
  output: ${user.home}/data/output
  file-type: .dat
  sufix-result-done: .done
  delimiter-reader-default: ç
  delimiter-writer-default: ç
  initial-delay-string: PT10S
  fixed-delay-string: PT20S

```

**O projeto usa:**
- JDK 15
- Gradle 6.6
- Spring boot, Batch, data JPA
- lombok
- mysql | h2

