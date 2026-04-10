
-- aqui eu inserir os nomes nas tabelas para fazer uns teste, e aprender como funiona o banco de dados e os repositorios


INSERT INTO aventura.aventureiro (
    id, organizacao_id, usuario_id, nome, classe, nivel, ativo, data_criacao, data_atualizacao
) VALUES
      (1, 1, 1, 'Aventureiro_1', 'GUERREIRO', 1, true, '2026-03-17 00:49:00', '2026-03-17 00:49:00'),
      (2, 1, 1, 'Aventureiro_2', 'ARQUEIRO', 8, true, '2026-03-17 00:49:00', '2026-03-17 00:49:00'),
      (3, 1, 1, 'Aventureiro_3', 'MAGO', 5, true, '2026-03-17 00:49:00', '2026-03-17 00:49:00'),
      (4, 1, 1, 'Aventureiro_4', 'GUERREIRO', 22, true, '2026-03-17 00:49:00', '2026-03-17 00:49:00');



INSERT INTO aventura.companheiro (aventureiro_id, nome, especie, indice_lealdade) VALUES
                                                                               (4, 'Comp_4', 'DRAGAO_MINIATURA', 59),
                                                                               (1, 'Zion', 'LOBO', 100);




INSERT INTO aventura.missao (id, organizacao_id, titulo, nivel_perigo, status, data_criacao, data_inicio, data_fim) VALUES
                                                                                                                        (1, 1, 'Resgate na Floresta', 'MEDIO', 'PLANEJADA', '2026-03-17', '2026-04-01 08:00:00', '2026-04-05 18:00:00'),
                                                                                                                        (2, 1, 'Abater Goblin Rei', 'ALTO', 'CONCLUIDA', '2026-03-17', '2026-04-05 10:00:00', '2026-04-10 20:00:00'),
                                                                                                                        (3, 1, 'Defesa da Vila', 'ALTO', 'CONCLUIDA', '2026-03-17', '2026-04-10 12:00:00', '2026-04-15 15:00:00'),
                                                                                                                        (4, 1, 'Exploração de Ruína', 'MEDIO', 'CONCLUIDA', '2026-03-17', '2026-04-15 09:00:00', '2026-04-20 17:00:00'),
                                                                                                                        (5, 1, 'Dragão de Sangue', 'CRITICO', 'EM_ANDAMENTO', '2026-03-17', '2026-04-20 07:00:00', '2026-04-30 23:59:59');



INSERT INTO aventura.participacao_missao (missao_id, aventureiro_id, papel, recompensa_ouro, destaque, data_registro) VALUES
                                                                                                                                             (1, 1, 'ATAQUE', 916.00, true, '2026-05-24 21:41:05'),
                                                                                                                                             (5, 1, 'LIDER', 1500.00, true, '2026-04-20 08:00:00'),
                                                                                                                                             (2, 2, 'SUPORTE', 1250.00, true, '2026-04-04 21:09:36');






select * from aventura.aventureiro;
select * from aventura.companheiro;
select * from aventura.missao;
select * from aventura.participacao_missao;


DROP table aventura.aventureiro , aventura.companheiro , aventura.missao , aventura.participacao_missao;



-- aqui esta o truncate para limpar as tabelas
TRUNCATE TABLE
    aventura.participacao_missao,
    aventura.companheiro,
    aventura.aventureiro,
    aventura.missao
    RESTART IDENTITY CASCADE;

TRUNCATE table aventura.companheiro RESTART IDENTITY CASCADE;