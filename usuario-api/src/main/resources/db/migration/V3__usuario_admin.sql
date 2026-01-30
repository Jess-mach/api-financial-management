INSERT INTO public.usuarios (id,email,login,nome,perfil_usuario,senha)
VALUES ('eb3e748e-1e9e-42cb-9473-c18445e41a3e'::uuid,
        'jessica.costa@ntt-fake.com.br',
        'jessica.costa',
        'Jessica Costa',
        'ADMINISTRADOR',
        '$2a$10$wXQQjqgU1IEDZriNLCP2FuwVlaERc641i2U3H.9ypL0pjcGFI1BL6')
ON CONFLICT (id) DO NOTHING;