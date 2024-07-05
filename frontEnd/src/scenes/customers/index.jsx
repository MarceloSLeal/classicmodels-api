import React, { useState, useEffect } from 'react';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import { GridRowModes, DataGrid, GridToolbarContainer, GridActionsCellItem }
  from "@mui/x-data-grid";
import Button from '@mui/material/Button';
import { Box, useTheme } from "@mui/material";
import { tokens } from "../../theme";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import useFetchData from "../../api/getData";
import { useNavigate } from "react-router-dom";

const Customers = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const url = Urls();
  const { data, loading, error } = useFetchData(url.customers.findAll_Post);
  const [rowModesModel, setRowModesModel] = React.useState({});
  const [rows, setRows] = useState([]);
  useEffect(() => {
    if (data) {
      setRows(data);
    }
  }, [data]);

  const EditToolbar = () => {
    const navigate = useNavigate();

    const handleClick = () => {
      navigate("/formaddcustomer");
    }

    return (
      <GridToolbarContainer>
        <Button
          sx={{
            backgroundColor: colors.blueAccent[700],
            color: colors.primary[100],
            paddingTop: '10px', paddingRight: '10px',
            paddingBottom: '10px', paddingLeft: '10px',
          }}
          startIcon={<AddIcon />} onClick={handleClick}>
          Add record
        </Button>
      </GridToolbarContainer>
    );
  }

  {/*--TODO Editar os eventos dos botoes de editar e deletar para encaminhar para
   as respectivas paginas e enviar os valores das linhas por parametro */}
  const handleEditClick = (id) => () => {
    setRowModesModel({ ...rowModesModel, [id]: { mode: GridRowModes.Edit } });
  };
  const handleDeleteClick = (id) => () => {
    setRows(rows.filter((row) => row.id !== id));
  };

  const columns = [
    { field: "id", headerName: "ID", flex: 0.5 },
    { field: "name", headerName: "NAME", flex: 1, cellClassName: "name-column--cell" },
    { field: "email", headerName: "EMAIL", flex: 1 },
    { field: "contactLastName", headerName: "CONT.LAST NAME", flex: 1 },
    { field: "contactFirstName", headerName: "CONT.FIRST NAME", flex: 1 },
    { field: "phone", headerName: "PHONE", flex: 1 },
    { field: "addressLine1", headerName: "ADRESS1", flex: 1 },
    { field: "addressLine2", headerName: "ADRESS2", flex: 1 },
    { field: "city", headerName: "CITY", flex: 1 },
    { field: "state", headerName: "STATE", flex: 1 },
    { field: "postalCode", headerName: "P.CODE", flex: 1 },
    { field: "country", headerName: "COUNTRY", flex: 1 },
    { field: "creditLimit", headerName: "CREDIT", flex: 1 },
    { field: "employeeId", headerName: "EMP.ID", flex: 1 },

    {
      field: 'actions',
      type: 'actions',
      headerName: 'Actions',
      width: 100,
      cellClassName: 'actions',
      //--TODO adicionar ao parametro as variáveis da linha do datagrid
      getActions: ({ id }) => {

        return [
          <GridActionsCellItem
            icon={<EditIcon />}
            label="Edit"
            className="textPrimary"
            onClick={handleEditClick(id)}
            color="inherit"
          />,
          <GridActionsCellItem
            icon={<DeleteIcon />}
            label="Delete"
            onClick={handleDeleteClick(id)}
            color="inherit"
          />,
        ];
      },
    },
  ];

  if (loading) {
    return (
      <Box m="20px">
        <Header title="CUSTOMERS" subtitle="Managing Customers" />
        <Box
          sx={{ fontSize: "2rem" }} >
          Loading...
        </Box>
      </Box>)
  }

  if (error) {
    return (
      <Box m="20px">
        <Header title="CUSTOMERS" subtitle="Managing Customers" />
        <Box>Error: {error.message}{}</Box>
      </Box>
    );
  }

  return (
    <Box m="20px">
      <Header title="CUSTOMERS" subtitle="Managing Customers" />
      <Box
        m="40px 0 0 0"
        height="75vh"
        sx={{
          "& .MuiDataGrid-root": {
            border: "none",
          },
          "& .MuiDataGrid-cell": {
            borderBottom: "none",
          },
          "& .name-column--cell": {
            color: colors.greenAccent[300],
          },
          "& .MuiDataGrid-columnHeader": {
            backgroundColor: colors.blueAccent[700],
            borderBottom: "none", fontSize: 14
          },
          "& .MuiDataGrid-virtualScroller": {
            backgroundColor: colors.primary[400],
          },
          "& .MuiDataGrid-footerContainer": {
            borderTop: "none",
            backgroundColor: colors.blueAccent[700],
          },
        }}
      >
        <DataGrid
          rows={data}
          columns={columns}
          rowModesModel={rowModesModel}
          slots={{
            toolbar: EditToolbar,
          }}
        />
      </Box>
    </Box>
  )
}

export default Customers;
