import React from 'react';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';

export default function HomePage() {
  return (
    <Box sx={{ py: 4, display: 'flex', flexDirection: 'column', alignItems: 'center', textAlign: 'center' }}>
      <Typography variant="h4">Página Inicial</Typography>
      <Typography>Bem-vindo à página inicial!</Typography>
    </Box>
  );
}
