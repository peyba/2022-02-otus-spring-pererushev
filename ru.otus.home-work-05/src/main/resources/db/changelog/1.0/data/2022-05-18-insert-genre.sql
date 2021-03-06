--liquibase formatted sql
--changeset a.pererushev:hw-05

INSERT INTO public.genre (id, code, name_eng, name_rus)
VALUES
    (1,     'CRIME',           'Crime',                                                'Криминальная проза, детектив'),
    (2,     'DETECTIVE',       'Detective fiction',                                    'Детектив'),
    (3,     'SCIENCE',         'Science fiction',                                      'Научная фантастика'),
    (4,     'POST_APOC',       'Post-apocalyptic',                                     'Постапокалипсис'),
    (5,     'DISTOPIA',        'Distopia',                                             'Антиутопия'),
    (6,     'CYBERPUNK',       'Cyberpunk',                                            'Киберпанк'),
    (7,     'FANTASY',         'Fantasy',                                              'Фэнтези'),
    (8,     'ROMANCE',         'Romance novel, Romantic novel',                        'Любовный роман'),
    (9,     'WESTERN',         'Western',                                              'Вестерн'),
    (10,    'HORROR',          'Horror',                                               'Ужасы'),
    (11,    'CLASSIC',         'Classic',                                              'Классическая литература'),
    (12,    'FAIRY_TALE',      'Fairy tale',                                           'Сказка'),
    (13,    'FAN',             'Fan fiction',                                          'Фанфик'),
    (14,    'FOLKLORE',        'Folklore',                                             'Фольклор'),
    (15,    'HISTORICAL',      'Historical fiction',                                   'Историческая проза'),
    (16,    'HUMOR',           'Humor',                                                'Юмористическая проза'),
    (17,    'MYSTERY',         'Mystery',                                              'Детектив'),
    (18,    'PICTURE',         'Picture book',                                         'Книга с картинками (детские книжки)'),
    (19,    'THRILLER',        'Thriller',                                             'Триллер'),
    (20,    'EROTIC',          'Erotic fiction',                                       'Эротика'),
    (21,    'BIOGRAPY',        'Biograpy',                                             'Биография'),
    (22,    'AUTOBIOGRAPHY',   'Autobiography',                                        'Автобиография'),
    (23,    'ESSAY',           'Essay',                                                'Эссе'),
    (24,    'MANUAL',          'Owner''s manual, Instruction manual, User''s guide',   'Инструкция'),
    (25,    'JOURNALISM',      'Journalism',                                           'Публицистика'),
    (26,    'MEMOIR',          'Memoir',                                               'Мемуары'),
    (27,    'REFERENCE',       'Reference book',                                       'Справочник'),
    (28,    'HOW_TO',          'Self-help book, How-to book',                          'Руководство, саморазвитие'),
    (29,    'TEXTBOOK',        'Textbook',                                             'Учебник'),
    (30,    'ACADEMIC',        'Academic paper',                                       'Научное исследование'),
    (31,    'ENCYCLOPEDIA',    'Encyclopedia',                                         'Энциклопедия'),
    (32,    'DICTIONARY',      'Dictionary',                                           'Словарь'),
    (33,    'POPULAR',         'Popular science',                                      'Популярная наука'),
    (34,    'PHOTOGRAPH',      'Photograph',                                           'Фотокнига'),
    (35,    'TECHNICAL',       'Technical writing',                                    'Техническая литература'),
    (36,    'COOKBOOK',        'Cookbook',                                             'Кулинарная книга')