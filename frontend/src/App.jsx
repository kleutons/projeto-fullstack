import * as React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { AppProvider } from '@toolpad/core/react-router-dom';
import { DashboardLayout } from '@toolpad/core/DashboardLayout';
import HomeIcon from '@mui/icons-material/Home';
import PersonIcon  from '@mui/icons-material/Person';
import AssignmentIcon from '@mui/icons-material/Assignment';
import InfoIcon from '@mui/icons-material/Info';
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';

// Importar componentes de página
import HomePage from './pages/HomePage';
import TarefasPage from './pages/TarefasPage';
import AboutPage from './pages/AboutPage';
import NotFoundPage from './pages/NotFoundPage';
import LoginPage from './pages/LoginPage';
import ListarTarefa from './pages/tarefa/ListarTarefa';

// Definição de navegação
const NAVIGATION = [
  {
    kind: 'header',
    title: 'Menu items',
  },
  {
    segment: '',
    title: 'Página Inicial',
    icon: <HomeIcon />,
  },
  {
    segment: 'tarefas',
    title: 'Tarefas',
    icon: <AssignmentIcon />,
  },
  {
    segment: 'tarefa',
    title: 'Tarefa',
    icon: <AssignmentIcon />,
  },
  { 
    segment: 'login',
    title: 'Login',
    icon: <PersonIcon />, 
  },
  {
    segment: 'about',
    title: 'Sobre',
    icon: <InfoIcon />,
  },
  {
    segment: '404',
    title: 'Page 404',
    icon: <ErrorOutlineIcon />,
  },
];

function AppProviderBasic(props) {
  return (
    <AppProvider
      navigation={NAVIGATION}
      branding={{
        title: 'TAREFAS',
      }}
    >
      <DashboardLayout>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/tarefas" element={<TarefasPage />} />
          <Route path="/tarefa" element={<ListarTarefa />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/about" element={<AboutPage />} />
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </DashboardLayout>
    </AppProvider>
  );
}

function App() {
  return (
    <Router>
      <AppProviderBasic />
    </Router>
  );
}

export default App;
