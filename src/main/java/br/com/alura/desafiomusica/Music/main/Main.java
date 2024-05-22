package br.com.alura.desafiomusica.Music.main;

import br.com.alura.desafiomusica.Music.model.Artista;
import br.com.alura.desafiomusica.Music.model.Musica;
import br.com.alura.desafiomusica.Music.model.TipoArtista;
import br.com.alura.desafiomusica.Music.repository.ArtistaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private final ArtistaRepository repositorio;
    Scanner leitura = new Scanner(System.in);

    public Main(ArtistaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 9) {
            System.out.println("""
                *** Screen Sound Músicas ***
                
                1- Cadastrar artistas
                2- Cadastrar música
                3- Listar músicas
                4- Buscar músicas por artistas
                5- Pesquisar dados sobre um artista
                
                9- Sair""");
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtista();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicaPorArtista();
                    break;
                case 5:
                    pesquisarDadosSobreArtista();
                    break;
                case 9:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }

    }

    private void pesquisarDadosSobreArtista() {
        System.out.println("Sobre qual artista você deseja consultar os dados?");
        var nome = leitura.nextLine();
        Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nome);
        System.out.println(artista.get());

    }

    private void buscarMusicaPorArtista() {
        System.out.println("Qual artista você deseja ver as músicas?");
        var nomeArtista = leitura.nextLine();
        Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nomeArtista);
        if(artista.isEmpty()) {
            System.out.println("Artista não encontrado");
        }else {
            List<Musica> musicasEncontradas = artista.get().getMusicas();
            if(musicasEncontradas.isEmpty()) {
                System.out.println("Não há músicas cadastradas para esse artista");
            }else {
                musicasEncontradas.forEach(System.out::println);
            }
        }


    }

    private void listarMusicas() {
        List<Artista> artistas = repositorio.findAll();
        artistas.forEach(System.out::println);
    }

    private void cadastrarMusicas() {
        System.out.println("Cadastrar música de que artista?");
        var nome = leitura.nextLine();
        Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nome);
        if (artista.isPresent()) {
            System.out.println("Informe o título da música: ");
            var nomeMusica = leitura.nextLine();
            Musica musica = new Musica(nomeMusica);
            musica.setArtista(artista.get());
            artista.get().getMusicas().add(musica);
            repositorio.save(artista.get());
        }else {
            System.out.println("Artista não encontrado!");
        }
    }

    private void cadastrarArtista() {
        var cadastrarNovo = "S";

        while (cadastrarNovo.equalsIgnoreCase("s")) {
            System.out.println("Informe o nome desse artista: ");
            var nome = leitura.nextLine();
            System.out.println("Informe o tipo desse artista (solo, dupla ou banda)");
            var tipo = leitura.nextLine();
            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nome, tipoArtista);
            repositorio.save(artista);
            System.out.println("Cadastrar novo artista? (S/N)");
            cadastrarNovo = leitura.nextLine();
    }
    }
}
