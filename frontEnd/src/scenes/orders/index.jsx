import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { DataGrid, GridToolbarContainer } from "@mui/x-data-grid";
import { Box, useTheme } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import Button from "@mui/material/Button";
import Tooltip, { tooltipClasses } from '@mui/material/Tooltip';
import { styled } from '@mui/material/styles';

import Header from '../../components/Header';
import useFetchData from '../../api/getData';
import { Urls } from '../../api/Paths';
import { tokens } from "../../theme";

const Orders = () => {
  const url = Urls();
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const { data, loading, error } = useFetchData(url.orders.findAll_Post);
  const [rows, setRows] = useState([]);
  const navigateAdd = useNavigate();

  useEffect(() => {
    if (data) {
      setRows(data);
      console.log("Data", data);
      console.log(new Date("2003-01-06T00:00:00.000Z"));
    }
  }, [data]);

  console.log("Rows", rows);

  const EditToolBar = () => {
    const handleClick = () => {
      // TODO -- adiconar o caminho do form
      // navigateAdd("/formaddoffices")
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

  const GridActionTooltip = styled(({ className, ...props }) => (
    <Tooltip {...props} classes={{ popper: className }} />
  ))(() => ({
    [`& .${tooltipClasses.tooltip}`]: {
      backgroundColor: colors.primary[400],
      color: colors.primary[100],
      fontSize: 15,
      border: `1px solid ${colors.primary[100]}`,
    },
  }));

  const columns = [
    { field: "id", headerName: "ID", flex: 0.5 },
    {
      field: "date",
      headerName: "DATE",
      flex: 1,
      renderCell: (params) => {
        const date = new Date(params.value);
        return date instanceof Date && !isNaN(date) ? date.toLocaleString() : "Invalid Date";
      },
    },
    {
      field: "requiredDate",
      headerName: "REQUIRED DATE",
      flex: 1,
      renderCell: (params) => {
        const date = new Date(params.value);
        return date instanceof Date && !isNaN(date) ? date.toLocaleString() : "Invalid Date";
      },
    },
    {
      field: "shippedDate",
      headerName: "SHIPPED DATE",
      flex: 1,
      renderCell: (params) => {
        const date = new Date(params.value);
        return date instanceof Date && !isNaN(date) ? date.toLocaleString() : "Invalid Date";
      },
    },
    { field: "status", headerName: "STATUS", flex: 1 },
    { field: "comments", headerName: "COMMENTS", flex: 1 },
    { field: "customerId", headerName: "CUSTOMER ID", flex: 0.5 },
  ]

  if (loading) {
    return (
      <Box m="20px">
        <Header title="ORDERS" subtitle="Manage Orders" />
        <Box
          sx={{ fontSize: "2rem" }} >
          Loading...
        </Box>
      </Box>
    )
  }

  if (error) {
    return (
      <Box m="20px">
        <Header title="ORDERS" subtitle="Manage Orders" />
        <Box>Error: {error.message}{ }</Box>
      </Box>
    );
  }

  return (
    <Box m="20px">
      <Header title="ORDERS" subtitle="Manage Orders" />
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
            borderBottom: "none",
            fontSize: 14
          },
          "& .MuiDataGrid-virtualScroller": {
            backgroundColor: colors.primary[400],
          },
          "& .MuiDataGrid-footerContainer": {
            borderTop: "none",
            backgroundColor: colors.blueAccent[700],
          },
          "& .MuiDataGrid-toolbarContainer .MuiButton-text": {
            color: `${colors.grey[100]} !important`,
          },
        }}
      >
        <DataGrid
          rows={rows}
          columns={columns}
          slots={{
            toolbar: EditToolBar,
          }}
        />
      </Box>

    </Box>
  )

}

export default Orders;
