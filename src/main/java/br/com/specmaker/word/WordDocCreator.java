package br.com.specmaker.word;

import br.com.specmaker.azuredevops.AzureDevopsRestWorkItemClient;
import br.com.specmaker.entity.Query;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


@Component
public class WordDocCreator {

    private static final String FONT_CALIBRI = "Calibri";

    @Autowired
    private AzureDevopsRestWorkItemClient witRestClient;

    public XWPFDocument gerarArquivoEspecificacao(final Query devopsQuery)
            throws IOException, URISyntaxException, InvalidFormatException {

            var documento = new XWPFDocument();
            adicionarTitulo(documento, devopsQuery.getNome());
            addCabecalhoDocumento(documento);
            //adicionarSubTitulo(documento, dados.getSubTitulo());

            adicionarParagrafo(documento, " ", false, 16);

            devopsQuery.getWorkItems().forEach(p -> {
                String index = (devopsQuery.getWorkItems().indexOf( p ) + 1) + ". ";
                String titulo = index.concat( p.getTitulo() );
                adicionarParagrafo(documento, titulo, true, 14);
                adicionarParagrafo(documento, tratarDetalhesWorkItem( p.getDetalhes() ), false, 12);

                try {
                    adicionarImagem(documento, getWitImageBy("") );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                } catch (InvalidFormatException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });


            /*try (var out = new FileOutputStream( devopsQuery.getNome().concat(".docx") ) ) {
                documento.write(out);
            }*/

            return documento;

    }

    private void adicionarTitulo(final XWPFDocument documento, final String titulo) {
        XWPFParagraph paragrafoTitulo = documento.createParagraph();
        paragrafoTitulo.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun tituloRun = paragrafoTitulo.createRun();
        tituloRun.setText(titulo);
        tituloRun.setBold(true);
        tituloRun.setFontFamily(FONT_CALIBRI);
        tituloRun.setFontSize(20);
    }

    private void adicionarImagem(final XWPFDocument documento, final ByteArrayInputStream imagemStream)
            throws IOException, URISyntaxException, InvalidFormatException {

        XWPFParagraph paragrafoComImagem = documento.createParagraph();
        paragrafoComImagem.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun imagemRun = paragrafoComImagem.createRun();
        imagemRun.setTextPosition(20);

        imagemRun.addPicture(imagemStream, Document.PICTURE_TYPE_PNG,
                "", Units.toEMU(400), Units.toEMU(200));

    }

    private void adicionarParagrafo(final XWPFDocument documento, String conteudo, boolean bold, final int fontSize) {
        XWPFParagraph paragrafo = documento.createParagraph();
        paragrafo.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun secaoRun = paragrafo.createRun();
        secaoRun.setFontFamily(FONT_CALIBRI);
        secaoRun.setBold(bold);
        secaoRun.setFontSize(fontSize);
        secaoRun.setText(conteudo);
    }

    private void addCabecalhoDocumento(final XWPFDocument documento) {

        var tabela = documento.createTable();
        XWPFTableRow linha = tabela.getRow(0);
        linha.getCell(0).setText("Projeto");
        linha.addNewTableCell().setText("Nome do Projeto");
        linha.addNewTableCell().setText("Analista responsável");
        linha.addNewTableCell().setText("Nome e e-mail Analista responsável");

        linha = tabela.createRow();
        linha.getCell(0).setText("Data da solicitação");
        linha.getCell(1).setText(
                LocalDateTime.now().format( DateTimeFormatter.ofPattern("dd/MM/uuuu") ) );
        linha.getCell(2).setText("Pedido (uso do Sesc)");
        linha.getCell(3).setText("");

        linha = tabela.createRow();
        linha.getCell(0).setText("Gerência / Unidade Solicitante");
        linha.getCell(1).setText( "" );
        linha.getCell(2).setText("Contato");
        linha.getCell(3).setText("");

    }

    private String tratarDetalhesWorkItem(String descricao){
        String str = !Objects.isNull(descricao) ? descricao.replaceAll("<.*?>", "") : "";
        str = str.replaceAll("&"+"nbsp;", " ");
        str = str.replaceAll(String.valueOf((char) 160), " ");
        str = str.replace("\u00a0","");

        return str;
    }

    private ByteArrayInputStream getWitImageBy(String imageId)
            throws IOException, ExecutionException, InterruptedException {
        byte[] witImg = witRestClient.retrieveImageFromWorkItemById(
                "950f0c34-5dca-4a2a-82b0-6135b43afbc7");

        ByteArrayInputStream imageStream = new ByteArrayInputStream(witImg);
        return imageStream;
    }
}
