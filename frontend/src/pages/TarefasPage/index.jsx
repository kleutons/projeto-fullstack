import React, { useState } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button } from '@mui/material';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';

const initialRows = [ 
  createData(1, 'Tarefa 1', 'Descrição da Tarefa', '2022-01-01', '2022-01-02', 'Concluída', 'Recurso 1'),
  createData(2, 'Tarefa 2', 'Outra Descrição', '2022-02-01', '2022-02-05', 'Em Andamento', 'Recurso 2'), 
  createData(3, 'Tarefa 3', 'Mais uma Descrição', '2022-03-01', '2022-03-10', 'Aguardando', 'Recurso 3'), 
  createData(4, 'Tarefa 4', 'Descrição adicional', '2022-04-01', '2022-04-02', 'Concluída', 'Recurso 4'), 
  createData(5, 'Tarefa 5', 'Descrição extensa', '2022-05-01', '2022-05-05', 'Em Andamento', 'Recurso 5'), 
  createData(6, 'Tarefa 6', 'Breve Descrição', '2022-06-01', '2022-06-10', 'Aguardando', 'Recurso 6'),
  createData(7, 'Tarefa 7', 'Outra Tarefa', '2022-07-01', '2022-07-15', 'Concluída', 'Recurso 7')
];

function createData(id, titulo, descricao, dataInicio, dataFim, status, recurso) {
  return { id, titulo, descricao, dataInicio, dataFim, status, recurso };
}


export default function TarefasPage() {

  const [rows, setRows] = useState(initialRows);

  const handleEdit = (id) => {
    console.log('Edit clicked', id);
    // lógica para editar tarefa
  };

  const handleDelete = (id) => {
    setRows(rows.filter((row) => row.id !== id));
  };


  return (
    <Box sx={{ py: 4,  maxWidth: '95%', margin: '0 auto', display: 'flex', flexDirection: 'column', alignItems: 'center', textAlign: 'center' }}>
      <Typography variant="h4" sx={{marginBottom:5}}>Tarefas</Typography>
        <hr/>
        <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>ID</TableCell>
                <TableCell align="right">Título</TableCell>
                <TableCell align="right">Descrição</TableCell>
                <TableCell align="right">Data de Início</TableCell>
                <TableCell align="right">Data de Finalização</TableCell>
                <TableCell align="right">Status</TableCell>
                <TableCell align="right">Recurso</TableCell>
                <TableCell align="right">Ações</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {rows.map((row) => (
                <TableRow key={row.id}>
                  <TableCell component="th" scope="row">
                    {row.id}
                  </TableCell>
                  <TableCell align="right">{row.titulo}</TableCell>
                  <TableCell align="right">{row.descricao}</TableCell>
                  <TableCell align="right">{row.dataInicio}</TableCell>
                  <TableCell align="right">{row.dataFim}</TableCell>
                  <TableCell align="right">{row.status}</TableCell>
                  <TableCell align="right">{row.recurso}</TableCell>
                  <TableCell align="right">
                    <Button variant="contained" color="primary" onClick={() => handleEdit(row.id)}><EditIcon fontSize="small" /></Button>
                    <Button variant="contained" color="secondary" onClick={() => handleDelete(row.id)} sx={{ ml: 1 }}><DeleteIcon fontSize="small" /></Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
    </Box>
  );
}