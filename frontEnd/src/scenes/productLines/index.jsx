import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";

import { GridActionsCellItem } from "@mui/x-data-grid";
import { Box, useTheme } from "@mui/material";
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import EditIcon from '@mui/icons-material/Edit';
import Tooltip, { tooltipClasses } from '@mui/material/Tooltip';
import { styled } from '@mui/material/styles';

import Header from "../../components/Header";
import useFetchData from "../../api/getData";
import { Urls } from "../../api/Paths";
import { tokens } from "../../theme";
import ConfirmDeleteDialog from "../../components/dialogs/ConfirmDeleteDialog"
import BoxDataGrid from "../../components/boxes/BoxDataGrid"

const ProductLines = () => {
  const urlData = Urls();
  const theme = useTheme();
  const { data, loading, error } = useFetchData(urlData.productlines.findAll_Post);
  const [dialogConfirmOpen, setDialogConfirmOpen] = useState(false);
  const [dialogDeleteOpen, setDialogDeleteOpen] = useState(false);
  const [idDelete, setIdDelete] = useState(null);
  const colors = tokens(theme.palette.mode);
  const [status, setStatus] = useState('');
  const [rows, setRows] = useState([]);
  const navigateEdit = useNavigate();

  const handleEditDatagridButton = (params) => () => {
    const rowData = params.row;
    // TODO -- criar página formeditproductlines
    // navigateEdit("/formeditcustomer", { state: { rowData } });
  };
  const handleDeleteDatagridButton = (params) => () => {
    setIdDelete(params.id);

    setDialogConfirmOpen(true);
  }

  const handleCloseConfirm = () => {
    setDialogConfirmOpen(false);
  }

  const handleDeleteConfirm = async () => {
    setDialogConfirmOpen(false);
    const urlDelete = Urls(idDelete);

    setStatus('');

    try {
      // TODO -- alterar o caminho para deletar productLines
      const response = await fetch(urlDelete.customers.findById_Put_Delete, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      setStatus(response.status);

      if (response.status === 204) {
        setStatus(`${response.status} Product Line Deleted Succesfully!`);

        setRows((prevData) => prevData.filter((item) => item.id !== idDelete));
      } else {
        setStatus(`Error: ${response.status} Failed to delete Product Line`);
      }
    } catch (error) {
      setStatus(`Error: ${error.message} Failed to delete Procut Line`);
    }

    setDialogDeleteOpen(true);
  }

  const handleCloseDelete = () => {
    setDialogDeleteOpen(false);
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

  // TODO -- adicionar para a tela orders na coluna comments essa configuração de 
  // quebra de linha para textos grandes
  const columns = [
    { field: "productLine", headerName: "PRODUCT LINE", flex: 0.5, cellClassName: "name-column-cell" },
    {
      field: "textDescription",
      headerName: "TEXT DESCRIPTION",
      flex: 1.5,
      minWidth: 150,
      renderCell: (params) => (
        <div
          style={{
            maxHeight: 200,
            overflowY: 'auto',
            whiteSpace: 'normal', // Permite quebra de linha
            wordWrap: 'break-word',
            lineHeight: 2,
          }}
        >
          {params.value}
        </div>
      ),
    },
    { field: "htmlDescrition", headerName: "HTML DESCRIPTION", flex: 1 },
    { field: "image", headerName: "IMAGE", flex: 1 },

    {
      field: 'actions',
      type: 'actions',
      headerName: 'ACTIONS',
      width: 100,
      cellClassName: 'actions',
      getActions: (params) => {

        return [

          <GridActionTooltip title="Edit this Product Line"
            placement="bottom">
            <GridActionsCellItem
              icon={<EditIcon />}
              label="Edit"
              className="textPrimary"
              onClick={handleEditDatagridButton(params)}
              color="inherit"
            />
          </GridActionTooltip>,

          <GridActionTooltip title="Delete this Product Line"
            placement="bottom">
            <GridActionsCellItem
              icon={<DeleteIcon />}
              label="Delete"
              onClick={handleDeleteDatagridButton(params)}
              color="inherit"
            />
          </GridActionTooltip>,
        ];
      },
    },
  ]

  useEffect(() => {
    if (data) {
      setRows(data);
    }
  }, [data]);

  if (loading) {
    return (
      <Box m="20px">
        <Header title="PRODUCT LINES" subtitle="Manage PRODUCT LINES" />
        <Box
          sx={{ fontSize: "2rem" }} >
          Loading...
        </Box>
      </Box>)
  }

  if (error) {
    return (
      <Box m="20px">
        <Header title="PRODUCT LINES" subtitle="Manage PRODUCT LINES" />
        <Box>Error: {error.message}{ }</Box>
      </Box>
    );
  }

  return (
    <Box m="20px">
      <Header title="PRODUCT LINES" subtitle="Manage Product Lines" />

      <BoxDataGrid
        colors={colors}
        rows={rows}
        columns={columns}
        path={"/formaddproductlines"}
        rowId={"productLine"}
        rowHeight={200}
      />

      <ConfirmDeleteDialog
        dialogConfirmOpen={dialogConfirmOpen} handleCloseConfirm={handleCloseConfirm}
        contextText={"ID:"} contextVar={idDelete} handleDeleteConfirm={handleDeleteConfirm}
        dialogDeleteOpen={dialogDeleteOpen} handleCloseDelete={handleCloseDelete}
        status={status}
      />

    </Box>
  )
}

export default ProductLines;
