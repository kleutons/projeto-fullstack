import React from 'react';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';

export default function NotFoundPage() {
  return (
    <Box sx={{ py: 4, display: 'flex', flexDirection: 'column', alignItems: 'center', textAlign: 'center' }}>
      <Typography variant="h4">404 - Não Encontrado</Typography>
      <Typography>A página que você está procurando não existe.</Typography>
    </Box>
  );
}
