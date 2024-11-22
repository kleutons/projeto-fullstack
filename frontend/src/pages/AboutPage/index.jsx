import React from 'react';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Link from '@mui/material/Link';
import WhatsAppIcon from '@mui/icons-material/WhatsApp';
import LinkedInIcon from '@mui/icons-material/LinkedIn';
import GitHubIcon from '@mui/icons-material/GitHub';
import Grid from '@mui/material/Grid';

export default function AboutPage() {
  return (
    <Box sx={{ py: 4, maxWidth: '95%', margin: '0 auto', display: 'flex', flexDirection: 'column', alignItems: 'center', textAlign: 'center' }}>
      <Typography variant="h4" gutterBottom>Sobre</Typography>
      <Box
        component="img"
        sx={{
          height: 100,
          width: 100,
          borderRadius: '50%',
          marginTop: 2,
          marginBottom: 2,
        }}
        alt="Cleuton Novais"
        src="https://avatars.githubusercontent.com/u/106082564"
      />
      <Typography>
        Olá! Eu sou Cleuton Novais, mais conhecido como Kleuton. Sou programador e um verdadeiro entusiasta da tecnologia.
        <br />
        Este aplicativo é um CRUD desenvolvido como parte de um projeto fullstack para minha faculdade, focado na organização de tarefas.
      </Typography>
      <Typography sx={{ mt: 2 }}>
        Sinta-se à vontade para entrar em contato comigo através das minhas redes sociais:
      </Typography>
      <Grid container spacing={2} sx={{ mt: 4, justifyContent: 'center' }}>
        <Grid item>
          <Link href="https://github.com/kleutons" target="_blank" rel="noopener noreferrer" sx={{ display: 'flex', alignItems: 'center' }}>
            <GitHubIcon sx={{ mr: 1 }} />
            Github
          </Link>
        </Grid>
        <Grid item>
          <Link href="https://api.whatsapp.com/send/?phone=5574999116684" target="_blank" rel="noopener noreferrer" sx={{ display: 'flex', alignItems: 'center' }}>
            <WhatsAppIcon sx={{ mr: 1 }} />
            WhatsApp
          </Link>
        </Grid>
        <Grid item>
          <Link href="https://www.linkedin.com/in/kleuton-novais/" target="_blank" rel="noopener noreferrer" sx={{ display: 'flex', alignItems: 'center' }}>
            <LinkedInIcon sx={{ mr: 1 }} />
            LinkedIn
          </Link>
        </Grid>
      </Grid>
    </Box>
  );
}
