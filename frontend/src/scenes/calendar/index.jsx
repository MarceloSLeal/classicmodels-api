import { useState, useEffect } from "react";

import FullCallendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import listPlugin from "@fullcalendar/list";
import multiMonthPlugin from '@fullcalendar/multimonth'
import {
  Box, List, ListItem, ListItemText, Typography, useTheme
} from "@mui/material";

import Header from "../../components/Header";
import { tokens } from "../../theme";
import useFetchData from "../../api/getData";
import { Urls } from "../../api/Paths";
import PostForms from "../../components/formsRequests/PostForms";
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog";

const Calendar = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const [currentEvents, setCurrentEvents] = useState([]);
  const urlData = Urls();
  const { data, loading, error } = useFetchData(urlData.calendar.findAll_Post);
  const [responseCode, setResponseCode] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');

  const timeFormat = {
    hour: "2-digit",
    minute: "2-digit",
    hour12: false,
  }

  const calendarViews = [
    "dayGridMonth",
    "timeGridWeek",
    "timeGridDay",
    "listMonth",
    "multiMonthYear",
  ];

  const views = Object.fromEntries(
    calendarViews.map((view) => [view, { eventTimeFormat: timeFormat, slotLabelFormat: timeFormat }])
  );

  const formatEventTime = (start, end, timeFormat) => {
    const startDate = new Date(start);
    const endDate = new Date(end);

    const sameDay =
      startDate.getFullYear() === endDate.getFullYear() &&
      startDate.getMonth() === endDate.getMonth() &&
      startDate.getDate() === endDate.getDate();

    const startStr = formatDate(startDate, {
      year: "numeric",
      month: "short",
      day: "numeric",
      ...timeFormat,
    });

    const endStr = formatDate(endDate, sameDay ? timeFormat : {
      year: "numeric",
      month: "short",
      day: "numeric",
      ...timeFormat,
    });

    return `${startStr} | ${endStr}`;
  };

  const formatDate = (date) => {
    // Para garantir o offset local (ex: -03:00), usamos Intl API
    const pad = (n) => (n < 10 ? '0' + n : n);
    const offset = -date.getTimezoneOffset();
    const sign = offset >= 0 ? '+' : '-';
    const hours = pad(Math.floor(Math.abs(offset) / 60));
    const minutes = pad(Math.abs(offset) % 60);

    return date.getFullYear() +
      '-' + pad(date.getMonth() + 1) +
      '-' + pad(date.getDate()) +
      'T' + pad(date.getHours()) +
      ':' + pad(date.getMinutes()) +
      ':' + pad(date.getSeconds()) +
      sign + hours + ':' + minutes;
  };

  const handleDateClick = (selected) => {
    const title = prompt("Please enter a new title for your event");
    const calendarApi = selected.view.calendar;
    calendarApi.unselect();

    if (title) {

      let start = selected.start;
      let end = selected.end;

      if (selected.allDay) {
        const startDate = new Date(start);
        startDate.setHours(6, 0, 0, 0);

        const endDate = new Date(start);
        endDate.setHours(20, 0, 0, 0);

        start = startDate;
        end = endDate;
      }

      const uuid = crypto.randomUUID();

      const event = calendarApi.addEvent({
        id: uuid,
        title,
        start,
        end,
        allDay: false,
      });

      console.log({
        id: event.id,
        title: event.title,
        start: formatDate(event.start),
        end: formatDate(event.end),
        allDay: event.allDay
      })

      handleEventPost(event);
    }

  };

  const handleEventClick = (selected) => {
    if (
      window.confirm(
        `Are you sure you want to delete the event '${selected.event.title}'`
      )
    ) {
      selected.event.remove();
    }
  };

  const handleEventPost = async (values) => {
    try {

      const response = await PostForms(values, urlData.calendar.findAll_Post);
      const data = await response.json();

      setResponseCode(response.status);

      if (!response === 201) {
        setStatus(`Error: ${data.tittle || 'Failed to create event'} - ${data.detail || ''}`);
        setDialogOpen(true);
      }

    } catch (error) {
      setStatus(`Error: ${error.message || 'Failed to create event'}`);
      setDialogOpen(true);
    }
  }

  const handleClose = () => {
    setDialogOpen(false);
  }

  useEffect(() => {
    if (data) {
      console.log(data);
    }
  }, [data]);

  return <Box m="20px">
    <Header title="CALENDAR" subtitle="Full calendar Interactive Page" />

    <Box display="flex" justifyContent="space-between">
      {/* CALENDAR SIDEBAR */}
      <Box
        flex="1 1 20%"
        backgroundColor={colors.primary[400]}
        p="15px"
        borderRadius="4px"
      >
        <Typography variant="h5">Events</Typography>
        <List>
          {currentEvents.map((event) => (
            <ListItem
              key={event.id}
              sx={{
                backgroundColor: colors.greenAccent[500],
                margin: "10px 0",
                borderRadius: "2px",
              }}
            >
              <ListItemText
                primary={event.title}
                secondary={
                  <Typography>
                    {formatEventTime(event.start, event.end, timeFormat)}
                  </Typography>
                }
              />
            </ListItem>
          ))}
        </List>
      </Box>

      {/* CALENDAR */}
      <Box flex="1 1 100%" ml="15px">
        <FullCallendar
          slotMinTime="06:00:00"
          slotMaxTime="20:00:00"
          height="75vh"
          plugins={[
            dayGridPlugin,
            timeGridPlugin,
            interactionPlugin,
            listPlugin,
            multiMonthPlugin
          ]}
          headerToolbar={{
            left: "prev,next,today",
            center: "title",
            right: "multiMonthYear,dayGridMonth,timeGridWeek,timeGridDay,listMonth"
          }}
          initialView="dayGridMonth"
          editable={true}
          selectable={true}
          selectMirror={true}
          dayMaxEvents={true}
          select={handleDateClick}
          eventClick={handleEventClick}
          eventsSet={(events) => setCurrentEvents(events)}
          events={data || []}

          eventTimeFormat={timeFormat}
          views={views}

        />

        <style>
          {`
      .fc-multimonth .fc-daygrid-day-number {
        color: ${colors.grey[600]};
      }

      .fc-multimonth .fc-day-today .fc-daygrid-day-number {
        color: #d32f2f;
      }
    `}
        </style>

      </Box>
    </Box>

    <OperationStatusDialog
      dialogOpen={dialogOpen} onClose={handleClose} status={status}
      responseCode={responseCode} onClick={handleClose}
    />

  </Box>
};

export default Calendar;
