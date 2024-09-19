import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { Box, useTheme } from "@mui/material";
import Tooltip, { tooltipClasses } from '@mui/material/Tooltip';
import { styled } from '@mui/material/styles';

import Header from '../../components/Header';
import useFetchData from '../../api/getData';
import { Urls } from '../../api/Paths';
import { tokens } from "../../theme";
import BoxDataGrid from "../../components/boxes/BoxDataGrid"
import { GridActionsCellItem } from '@mui/x-data-grid';
import EditIcon from "@mui/icons-material/Edit";

const Orders = () => {
  const url = Urls();
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const { data, loading, error } = useFetchData(url.orders.findAll_Post);
  const [rows, setRows] = useState([]);
  const navigateEdit = useNavigate();

  // TODO -- adiconar página de edit e caminho
  const handleEditDataGridButton = (params) => () => {
    const rowData = params.row;
    navigateEdit("/formeditorders", { state: { rowData } });
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
      field: "shippedDate",
      headerName: "SHIPPED DATE",
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
    { field: "status", headerName: "STATUS", flex: 1 },
    { field: "comments", headerName: "COMMENTS", flex: 1 },
    { field: "customerId", headerName: "CUSTOMER ID", flex: 0.5 },

    {
      field: 'actions',
      type: 'actions',
      headerName: 'ACTIONS',
      width: 100,
      cellClassName: 'actions',
      getActions: (params) => {

        return [
          <GridActionTooltip title="Edit Order" placement="bottom">
            <GridActionsCellItem
              icon={<EditIcon />}
              label="Edit"
              className="textPrimary"
              onClick={handleEditDataGridButton(params)}
              color="inherit"
            />
          </GridActionTooltip>
        ];
      },
    },
  ];

  useEffect(() => {
    if (data) {
      setRows(data);
    }
  }, [data]);

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

      <BoxDataGrid
        colors={colors}
        rows={rows}
        columns={columns}
        path={"/formaddorders"}
      />

    </Box>
  )

}

export default Orders;
